package CompWeb.Homepage.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//동아리 소개 탭의 연혁관리를 위한 Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;
}
