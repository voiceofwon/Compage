package CompWeb.Homepage.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddHistoryDTO {
    private Long id;
    @NotBlank(message = "내용을 입력하세요.")
    private String content;
}
