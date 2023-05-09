package CompWeb.Homepage.DTO;

import CompWeb.Homepage.Entity.TopFixedFile;
import CompWeb.Homepage.Entity.TopFixedPost;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetTopFixedPostDTO {
    private Long id;

    private String author;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private TopFixedFile topFixedFile;


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
    public GetTopFixedPostDTO(Long id, String author, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, TopFixedFile file){
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.topFixedFile = file;
    }
}
