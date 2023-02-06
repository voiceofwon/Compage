package CompWeb.Homepage.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errormsg ="";

        if (exception instanceof AccountExpiredException) {
            errormsg ="accountExpired";
        } else if (exception instanceof CredentialsExpiredException) {
            errormsg = "credentialExpired";
        } else if (exception instanceof BadCredentialsException) {
            errormsg ="id 또는 비밀번호가 틀렸습니다.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("/member/login?error");

        if(!errormsg.equals("")){
            sb.append("=").append(errormsg);
        }

        response.sendRedirect(sb.toString());
    }
}
