package CompWeb.Homepage.Entity;

import jakarta.annotation.Nullable;
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
@Table(name = "sos_post")
//학습자료 게시판 게시글 Entity
public class SosPost extends Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @OneToOne(mappedBy ="sosPost",optional = false, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private File file;

    @Builder
    public SosPost(Long id, String author, String title, String content){
        this.id=id;
        this.setAuthor(author);
        this.setTitle(title);
        this.setContent(content);
    }
}
