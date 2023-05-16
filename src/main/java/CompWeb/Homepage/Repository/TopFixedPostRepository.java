package CompWeb.Homepage.Repository;


import CompWeb.Homepage.Entity.TopFixedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopFixedPostRepository extends JpaRepository<TopFixedPost,Long> {
    @Query("select DISTINCT o from TopFixedPost o join fetch o.topFixedFile")
    List<TopFixedPost> findAllJoinFetch();
}
