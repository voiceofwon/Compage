package CompWeb.Homepage.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter@Setter
@Table(name = "file")
//학습자료게시판 첨부파일 Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sosPost_id")
    private Long id;

    private String orgNm;
    private String savedNm;
    private String savedPath;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name ="sosPost_id")
    private SosPost sosPost;

}
