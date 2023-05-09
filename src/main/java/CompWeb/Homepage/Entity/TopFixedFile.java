package CompWeb.Homepage.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter@Setter
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
