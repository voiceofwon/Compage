package CompWeb.Homepage.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GetMemberDTO {
    private String name;
    private String username;
    private int grade;
    private String role;
    private LocalDateTime createdDate;

}
