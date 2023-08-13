package CompWeb.Homepage.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


//관리자 페이지 회원관리 탭에서 회원 조회를 위한 DTO
@Data
@Builder
@AllArgsConstructor
public class GetMemberDTO {
    private Long id;
    private String name;
    private String username;
    private String grade;
    private String role;
    private String phoneNum;
    private LocalDateTime createdDate;

}
