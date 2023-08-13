package CompWeb.Homepage.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter@Setter
//상단고정공지 첨부파일 Entity
public class TopFixedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topFixed_id")
    private Long id;

    private String orgNm;
    private String savedNm;
    private String savedPath;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name="topFixedPost_id")
    private TopFixedPost topFixedPost;
}
