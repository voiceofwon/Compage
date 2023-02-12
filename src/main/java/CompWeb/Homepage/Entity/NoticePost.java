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
public class NoticePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @OneToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name="id")
    private NoticeFile noticeFile;

    @Builder
    public NoticePost(Long id, String author, String title, String content){
        this.id=id;
        this.author=author;
        this.title=title;
        this.content=content;
    }
}
