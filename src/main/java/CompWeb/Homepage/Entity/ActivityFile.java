package CompWeb.Homepage.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "activityFile")
public class ActivityFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="activityPost_id")
    private Long id;

    private String orgNm;
    private String savedNm;
    private String savedPath;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name ="activityPost_id")
    private ActivityPost activityPost;
}
