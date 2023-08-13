package CompWeb.Homepage.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
//사이트 접속시 Interceptor에서 redis에 방문정보 임시저장 구현
public class SingleVisitInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //접속자 IP
        String userIp = request.getRemoteAddr();
        //접속 매체 (ex. edge,chrome)
        String userAgent = request.getHeader("User-Agent");
        //접속 일자
        String today = LocalDate.now().toString();
        String key = userIp +"_"+today;

        //String형의 key, value ValueOperation 생성
        ValueOperations valueOperations = redisTemplate.opsForValue();

        //redis에 접속자 정보 임시 저장, 이후에 redis에 저장된 정보를 DB에 옮기며 삭제되기 전까지
        //동일 IP 중복 저장 X
        if(!valueOperations.getOperations().hasKey(key)){
            valueOperations.set(key,userAgent);
        }

        return true;
    }
}
