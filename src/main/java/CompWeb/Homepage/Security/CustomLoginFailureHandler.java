package CompWeb.Homepage.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errormsg;

        if (exception instanceof InternalAuthenticationServiceException) {
            errormsg ="내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof CredentialsExpiredException) {
            errormsg = "세션이 만료되었습니다. 다시 시도해주세요.";
        } else if (exception instanceof BadCredentialsException) {
            errormsg ="아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        } else if(exception instanceof AuthenticationCredentialsNotFoundException){
            errormsg="인증요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else{
            errormsg="알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }

        errormsg = URLEncoder.encode(errormsg,"UTF-8");
        setDefaultFailureUrl("/member/login?error=true&exception="+errormsg);


        super.onAuthenticationFailure(request,response,exception);
    }
}
