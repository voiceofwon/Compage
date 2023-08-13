package CompWeb.Homepage.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //서버 컴퓨터 file directory에 접근하기 위한 url 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///home/compdna/Uploads/");

    }
}
