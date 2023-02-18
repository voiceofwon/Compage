package CompWeb.Homepage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberModifyDTO {

    private String name;
    private String username;
    private String password;
    private String grade;
}
