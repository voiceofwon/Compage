package CompWeb.Homepage.DTO;

import CompWeb.Homepage.Entity.File;
import CompWeb.Homepage.Entity.SosPost;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


//학습자료 게시판 게시글 조회를 위한 DTO
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetPostDTO {
    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private File file;

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
    public GetPostDTO(Long id, String author, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, File file) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.file =  file;
    }
}

