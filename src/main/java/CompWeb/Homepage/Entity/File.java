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
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orgNm;
    private String savedNm;
    private String savedPath;

    @OneToOne(mappedBy = "file",optional = false)
    private SosPost sosPost;
}
