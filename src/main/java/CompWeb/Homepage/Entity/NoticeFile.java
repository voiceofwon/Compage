package CompWeb.Homepage.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "noticeFile")
//공지사항 첨부파일 Entity
public class NoticeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="noticePost_id")
    private Long id;

    private String orgNm;
    private String savedNm;
    private String savedPath;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name="noticePost_id")
    private NoticePost noticePost;
}
