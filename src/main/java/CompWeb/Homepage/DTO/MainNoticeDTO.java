package CompWeb.Homepage.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MainNoticeDTO {

    private Long id;
    private Long title;

}
