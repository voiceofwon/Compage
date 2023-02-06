package CompWeb.Homepage;

import CompWeb.Homepage.DTO.MemberJoinDTO;
import CompWeb.Homepage.Repository.MemberRepository;
import CompWeb.Homepage.Service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class HomepageApplicationTests {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;



	@Test
	@Rollback(value = false)
	void contextLoads() {
		MemberJoinDTO memberJoinDTO = MemberJoinDTO.builder()
				.username("202126978")
				.password("202126978")
				.grade(3)
				.build();
		memberService.joinMember(memberJoinDTO);

		Assertions.assertEquals(memberService.findByUsername("202126978").get().getRoles().stream().toList(), List.of("USER"));


	}

	@Test
	void joinTest(){
		MemberJoinDTO memberJoinDTO = MemberJoinDTO.builder()
				.username("202126978")
				.password("202126978")
				.grade(3)
				.build();

		Long id = memberService.joinMember(memberJoinDTO);

		memberService.passwordMatches(Long.valueOf(1),memberRepository.findByUsername("202126978").get().getPassword());
		Assertions.assertEquals(id,1);
	}

}
