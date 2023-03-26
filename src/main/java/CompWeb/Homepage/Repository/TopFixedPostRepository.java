package CompWeb.Homepage.Repository;


import CompWeb.Homepage.Entity.TopFixedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopFixedPostRepository extends JpaRepository<TopFixedPost,Long> {
}
