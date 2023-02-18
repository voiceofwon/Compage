package CompWeb.Homepage.DTO;

import CompWeb.Homepage.Entity.ActivityPost;
import CompWeb.Homepage.Entity.SosPost;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ActivityPostDTO {
    private Long id;
    private String author;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private MultipartFile multipartFile;

    public ActivityPost toEntity(){
        ActivityPost build = ActivityPost.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public ActivityPostDTO(Long id, String author, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
