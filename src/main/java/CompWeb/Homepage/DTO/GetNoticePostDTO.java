package CompWeb.Homepage.DTO;

import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.NoticeFile;
import CompWeb.Homepage.Entity.SosPost;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetNoticePostDTO {

    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private NoticeFile file;

    public SosPost toEntity(){
        SosPost build = SosPost.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public GetNoticePostDTO(Long id, String author, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, NoticeFile file) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.file =  file;
    }
}
