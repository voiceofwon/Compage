package CompWeb.Homepage.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter@Setter
@Entity
//방문자수 집계를 위한 Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userIp;
    private String userAgent;
    private LocalDate date;
}
