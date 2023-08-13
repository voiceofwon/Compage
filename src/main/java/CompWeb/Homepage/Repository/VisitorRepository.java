package CompWeb.Homepage.Repository;

import CompWeb.Homepage.Entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    boolean existsByUserIpAndDate(String userIp, LocalDate date);

    List<Visitor> findByDate(LocalDate date);
}
