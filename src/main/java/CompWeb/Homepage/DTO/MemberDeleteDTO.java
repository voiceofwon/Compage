package CompWeb.Homepage.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//회원 삭제(탈퇴) data 교환을 위한 DTO
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDeleteDTO {
    private String username;
}
