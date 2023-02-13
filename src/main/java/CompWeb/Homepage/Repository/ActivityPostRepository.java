package CompWeb.Homepage.Repository;

import CompWeb.Homepage.Entity.ActivityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityPostRepository extends JpaRepository<ActivityPost,Long> {
}
