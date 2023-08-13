package CompWeb.Homepage.DTO;

import CompWeb.Homepage.Entity.ActivityFile;
import CompWeb.Homepage.Entity.ActivityPost;
import CompWeb.Homepage.Entity.NoticeFile;
import CompWeb.Homepage.Entity.SosPost;
import lombok.*;

import java.time.LocalDateTime;

//활동내역 게시글 조회 DTO
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetActivityPostDTO {
    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private ActivityFile file;

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
    public GetActivityPostDTO(Long id, String author, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, ActivityFile file) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.file =  file;
    }
}
