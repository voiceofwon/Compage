package CompWeb.Homepage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AdminJoinDTO {
    private String username;
    private String password;
    private int grade;
}
