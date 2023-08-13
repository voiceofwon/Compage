package CompWeb.Homepage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//관리자 페이지 회원관리 ->회원 정보 수정(학년)을 위한 DTO
@Data
@Builder
@AllArgsConstructor
public class MemberModifyDTO {

    private String name;
    private String username;
    private String password;
    private String grade;
}
