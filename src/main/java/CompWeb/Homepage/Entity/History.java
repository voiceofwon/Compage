package CompWeb.Homepage.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;
}
