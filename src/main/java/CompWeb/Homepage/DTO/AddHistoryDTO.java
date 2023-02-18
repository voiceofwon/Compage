package CompWeb.Homepage.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddHistoryDTO {
    private Long id;
    private String content;
}
