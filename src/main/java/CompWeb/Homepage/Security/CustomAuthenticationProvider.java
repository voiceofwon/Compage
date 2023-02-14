package CompWeb.Homepage.Security;

import CompWeb.Homepage.Entity.Member;
import CompWeb.Homepage.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

    private MessageSourceAccessor messages;

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        //비밀번호 확인
        Member user = memberService.loadUserByUsername(username);

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("password is not matched");
        }
        return new UsernamePasswordAuthenticationToken(username,password, authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
