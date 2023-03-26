package CompWeb.Homepage.DTO;


import CompWeb.Homepage.Entity.TopFixedPost;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter@Setter
@ToString
@NoArgsConstructor
public class TopFixedPostDTO {

    private Long id;

    private String author;

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public TopFixedPost toEntity(){
        TopFixedPost build = TopFixedPost.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public TopFixedPostDTO(Long id, String author, String title, String content, LocalDateTime createdDate,LocalDateTime modifiedDate){
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
