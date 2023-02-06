package CompWeb.Homepage.Service;

import CompWeb.Homepage.DTO.AdminJoinDTO;
import CompWeb.Homepage.DTO.MemberJoinDTO;
import CompWeb.Homepage.DTO.MemberModifyDTO;
import CompWeb.Homepage.Entity.Member;
import CompWeb.Homepage.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("["+username+"] Not Found"));
    }

    public Optional<Member> findByUsername(String username){
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public Member saveOrUpdate(Member member){
        return memberRepository.save(member);
    }

    public Member authenticate(String username, String password){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if(optionalMember.isPresent()){
            log.info("@Authenticate : PW 확인");
            Member member = optionalMember.get();
            if(!passwordEncoder.matches(password,member.getPassword())){
                throw new BadCredentialsException("비밀번호가 틀렸습니다.");
            }
        } else{
            throw new UsernameNotFoundException("User Not Found");
        }
        return optionalMember.get();
    }

    public void updateLoginInfo(Member member){
        log.info("@LoginSuccess : 로그인 정보 업데이트");
        member.setModifiedDate(LocalDateTime.now());
    }

    public boolean passwordMatches(Long id,String password){
        if(!passwordEncoder.matches(password, memberRepository.findById(id).get().getPassword())){
            return false;
        }
        return true;
    }

    @Transactional
    public Long joinMember(MemberJoinDTO memberLoginDTO){
        Member member = Member.builder()
                .username(memberLoginDTO.getUsername())
                .password(passwordEncoder.encode(memberLoginDTO.getPassword()))
                .grade(memberLoginDTO.getGrade())
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .roles(List.of("USER"))
                .build();
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long joinAdmin(AdminJoinDTO aminJoinDTO){
        Member member = Member.builder()
                .username(aminJoinDTO.getUsername())
                .password(passwordEncoder.encode(aminJoinDTO.getPassword()))
                .grade(aminJoinDTO.getGrade())
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .roles(List.of("ADMIN"))
                .build();
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long modifyMember(MemberModifyDTO memberModifyDTO){
        if(memberRepository.findByUsername(memberModifyDTO.getUsername()) != null){
            Member member = memberRepository.findByUsername(memberModifyDTO.getUsername()).get();
            member.setGrade(memberModifyDTO.getGrade());
            //수정사항 저장
            memberRepository.deleteById(member.getId());
            memberRepository.save(member);
            return member.getId();

        } else{
            throw new UsernameNotFoundException("["+memberModifyDTO.getUsername()+"] Not Found");
        }

    }

    @Transactional
    public List<Member> findByGrade(int grade){
        return memberRepository.findByGrade(grade);
    }

    @Transactional
    public List<Member> findByMemberId(String year){
        return memberRepository.findByUsernameContaining(year);
    }

    @Transactional
    public Long deleteMember(String username){
        Member deletingMember = memberRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("["+username+"] Not Found"));
        memberRepository.deleteByUsername(username);

        return deletingMember.getId();

    }

}
