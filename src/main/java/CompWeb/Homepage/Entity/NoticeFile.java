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
public class NoticeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orgNm;
    private String savedNm;
    private String savedPath;

    @OneToOne(mappedBy = "noticeFile",optional = false)
    private NoticePost noticePost;
}
