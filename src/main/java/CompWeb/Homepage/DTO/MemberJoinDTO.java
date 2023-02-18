package CompWeb.Homepage.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MemberJoinDTO {
    @NotBlank(message = "이름을 입력하세요.")
    private String name;
    @NotBlank(message = "학번을 입력하세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String grade;
    private String phoneNum;
}
