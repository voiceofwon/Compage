package CompWeb.Homepage.Entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notice_post")
//공지사항 게시글 Entity
public class NoticePost extends Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @OneToOne(mappedBy = "noticePost", cascade = CascadeType.ALL,optional = false)
    @PrimaryKeyJoinColumn
    private NoticeFile noticeFile;

    @Builder
    public NoticePost(Long id, String author, String title, String content){
        this.id=id;
        this.setAuthor(author);
        this.setTitle(title);
        this.setContent(content);
    }
}
