package CompWeb.Homepage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MemberJoinDTO {
    private String name;
    private String username;
    private String password;
    private int grade;
}
