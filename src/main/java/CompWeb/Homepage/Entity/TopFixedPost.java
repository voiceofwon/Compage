package CompWeb.Homepage.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "top_fixed_post")
//상단고정공지 Entity
public class TopFixedPost extends Post{

    @Id
    @GeneratedValue
    Long id;



    @OneToOne(mappedBy = "topFixedPost", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TopFixedFile topFixedFile;
    @Builder
    public TopFixedPost(Long id, String author, String title, String content){
        this.id=id;
        this.setAuthor(author);
        this.setTitle(title);
        this.setContent(content);
    }
}
