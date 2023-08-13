package CompWeb.Homepage.Configuration;

import CompWeb.Homepage.Interceptor.SingleVisitInterceptor;
import CompWeb.Homepage.Security.CustomLoginFailureHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig implements WebMvcConfigurer {

    //로그인 실패시 메시지 전달을 위한 handler
    private final CustomLoginFailureHandler customLoginFailureHandler;

    //방문자수 count service를 위한 VisitInterceptor
    private final SingleVisitInterceptor singleVisitInterceptor;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //Spring Security 아키텍쳐를 걸쳐 웹에 접근하게 되면 invalid한 사용자는 로그인 페이지로 redirect된다
    //홈 화면 및 소학회 소개 페이지 등 권한 없이도 접근 할 수 있도록 ignore설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/home","/intro/**","/","/intro/adminJoin","/activity",
                        "/Sos")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //Spring Security login 및 세션 설정 SecurityFilterChain Bean 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //CSRF
        http.csrf().disable();
        //로그인 요청 전 페이지로 redirect할 필요가 없어 requestCache disable 함.
        http.requestCache().disable();
        //url level별 접근권한 관리
        http.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                //로그인 페이지, 소학회 소개 페이지, 일반 게시글 목록 페이지는 누구나 접근가능
                .requestMatchers("/member/**","/","/intro/**").permitAll()
                //글 내용 조회 및 다운로드는 회원만 접근 가능
                .requestMatchers("/notice/noticePost/*","/notice",
                        "/Sos/post/*",
                        "/activity/activityPost/*").hasAnyRole("USER","ADMIN")
                //글 작성, 회원가입, 관리자 페이지 관련 서비스는 관리자 권한 사용자만 접근가능
                .requestMatchers("/notice/noticePost","/notice/noticePost/edit/**","/notice/noticePost/delete/**",
                        "/Sos/post","/Sos/post/edit/**","/Sos/post/delete/**",
                        "/activity/activityPost","/activity/activityPost/edit/**", "/activity/activityPost/delete/**",
                        "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated();

        //Spring Security에서 제공하는 로그인 api사용(세션)
        http.formLogin()
                //로그인 페이지 url
                .loginPage("/member/login")
                //login process url
                .loginProcessingUrl("/member/action")
                //로그인 실패 메시지를 위한 custom handler
                .failureHandler(customLoginFailureHandler)
                //로그인 성공시 redirect 주소
                .defaultSuccessUrl("/home");

        //로그아웃 처리
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //로그아웃시 로그인 화면으로 redirect
                .logoutSuccessUrl("/member/login")
                //로그아웃시 세션 invalidate
                .invalidateHttpSession(true)
                //로그아웃 시 header의 세션 쿠키 삭제
                .deleteCookies("JSESSIONID");


        //세션 설정
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionAuthenticationErrorUrl("/login?maximumSessions")
                //동시 로그인 불가
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                //세션 만료시 이동 페이지, 쿼리 스트링으로 값 전달
                .expiredUrl("/member/login?expiredSession");
        return http.build();
    }


    //방문자 관리를 위한 Interceptor url pattern override
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(singleVisitInterceptor)
                .addPathPatterns("/**/**/*")
                .addPathPatterns("/**/*")
                .addPathPatterns("/*")
                .addPathPatterns("/**");
    }

}
