package CompWeb.Homepage.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name ="activity_post")
//활동내역 게시판 Entity
public class ActivityPost extends Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;


    @OneToOne(mappedBy ="activityPost", cascade = CascadeType.ALL,optional = false)
    @PrimaryKeyJoinColumn
    private ActivityFile file;

    @Builder
    public ActivityPost(Long id, String author, String title, String content){
        this.id=id;
        this.setAuthor(author);
        this.setTitle(title);
        this.setContent(content);
    }
}
