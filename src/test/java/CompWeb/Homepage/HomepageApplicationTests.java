package CompWeb.Homepage;

import CompWeb.Homepage.DTO.AdminJoinDTO;
import CompWeb.Homepage.DTO.MemberJoinDTO;
import CompWeb.Homepage.Entity.NoticePost;
import CompWeb.Homepage.Entity.Post;
import CompWeb.Homepage.Repository.MemberRepository;
import CompWeb.Homepage.Repository.NoticeFileRepository;
import CompWeb.Homepage.Repository.NoticePostRepository;
import CompWeb.Homepage.Service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.ranges.Range;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
class HomepageApplicationTests {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private NoticePostRepository noticePostRepository;

	@Autowired
	private NoticeFileRepository noticeFileRepository;

	@Autowired
	private NoticePostService noticePostService;

	@Autowired
	private ActivityPostService activityPostService;

	@Autowired
	private SosPostService sosPostService;

	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Autowired
	private VisitorScheduler visitorScheduler;

	@Test
	@Rollback(value = false)
	void contextLoads() {
		AdminJoinDTO adminJoinDTO = AdminJoinDTO.builder()
				.name("김원중")
				.username("202126978")
				.password("202126978")
				.grade("3")
				.phoneNum("01063016963")
				.build();
		memberService.joinAdmin(adminJoinDTO);

		Assertions.assertEquals(memberService.findByUsername("202126978").get().getRoles().stream().toList(), List.of("USER"));


	}

	@Test
	void joinTest(){
		MemberJoinDTO memberJoinDTO = MemberJoinDTO.builder()
				.username("202126978")
				.password("202126978")
				.grade("3")
				.phoneNum("01063016963")
				.build();

		Long id = memberService.joinMember(memberJoinDTO);

		memberService.passwordMatches(Long.valueOf(1),memberRepository.findByUsername("202126978").get().getPassword());
		Assertions.assertEquals(id,1);
	}

	@Test
	@Transactional
	void test_notice_post_N1_problem(){
		List<NoticePost> posts = noticePostRepository.findAllJoinFetch();
		System.out.println("전체 공지문서는"+ posts.size() +"개이다.");
		NoticePost noticePost = noticePostRepository.findById(3L).get();
		System.out.println(noticePost.getTitle());
	}

	@Test
	@Transactional
	void notice_Service_sql_test_N1(){
		noticePostService.getPostList();
		noticePostService.getPost(3L);

	}

	@Test
	@Transactional
	void sos_Service_sql_test_N1(){
		sosPostService.getPostList();
		sosPostService.getPost(3L);
	}

	@Test
	@Transactional
	void activity_Service_sql_test_N1(){
		activityPostService.getPostList();
		activityPostService.getPost(3L);
	}

	@Test
	@Transactional
	void member_Service_sql_test_N1(){
		memberService.getMemberList();
		memberService.getMember(3L);
	}

	@Test
	@Transactional
	void Redis_connection_Test(){
		Set<String> keys = redisTemplate.keys("*");

		ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();

	}

	@Test
	@Transactional
	void visitorTest(){

	}

}
