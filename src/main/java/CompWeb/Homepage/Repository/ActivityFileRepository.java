package CompWeb.Homepage.Repository;

import CompWeb.Homepage.Entity.ActivityFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//활동내역
public interface ActivityFileRepository extends JpaRepository<ActivityFile,Long> {
}

