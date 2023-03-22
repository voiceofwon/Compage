package CompWeb.Homepage.Service;

import CompWeb.Homepage.DTO.AdminJoinDTO;
import CompWeb.Homepage.DTO.GetMemberDTO;
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
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Member loadUserByUsername(String username) throws UsernameNotFoundException {
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
    public Long getMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("["+id+"] Not Found"));

        return member.getId();
    }

    @Transactional
    public Long joinMember(MemberJoinDTO memberJoinDTO){
        Member member = Member.builder()
                .name(memberJoinDTO.getName())
                .username(memberJoinDTO.getUsername())
                .password(passwordEncoder.encode(memberJoinDTO.getPassword()))
                .grade(memberJoinDTO.getGrade())
                .phoneNum(memberJoinDTO.getPhoneNum())
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
                .name(aminJoinDTO.getName())
                .username(aminJoinDTO.getUsername())
                .password(passwordEncoder.encode(aminJoinDTO.getPassword()))
                .grade(aminJoinDTO.getGrade())
                .phoneNum(aminJoinDTO.getPhoneNum())
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
    public Member findById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("["+id+"] Not Found"));
    }
    @Transactional
    public List<GetMemberDTO> getMemberList(){
        List<Member> members = memberRepository.findAll();
        List<GetMemberDTO> memberList = new ArrayList<>();
        for(Member member : members){
            GetMemberDTO getMemberDTO = GetMemberDTO.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .username(member.getUsername())
                    .grade(member.getGrade())
                    .phoneNum(member.getPhoneNum())
                    .role(member.getRoles().toString())
                    .createdDate(member.getCreateDate())
                    .build();
            memberList.add(getMemberDTO);
        }
        Collections.sort(memberList,new MemberIdComparator());
        return memberList;
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
        memberRepository.deleteMemberByUsername(deletingMember.getUsername());

        return deletingMember.getId();

    }

}

class MemberIdComparator implements Comparator<GetMemberDTO>{
    @Override
    public int compare(GetMemberDTO o1, GetMemberDTO o2) {
        if(Integer.parseInt(o1.getUsername()) > Integer.parseInt(o2.getUsername())){
            return 1;
        } else if(Integer.parseInt(o1.getUsername()) < Integer.parseInt(o2.getUsername())){
            return -1;
        }
        return 0;
    }
}
