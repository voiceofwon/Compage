package CompWeb.Homepage.Repository;

import CompWeb.Homepage.Entity.SosPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SosPostRepository extends JpaRepository<SosPost,Long> {
}
