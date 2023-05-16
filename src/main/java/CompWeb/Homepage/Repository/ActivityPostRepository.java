package CompWeb.Homepage.Repository;

import CompWeb.Homepage.Entity.ActivityPost;
import CompWeb.Homepage.Entity.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityPostRepository extends JpaRepository<ActivityPost,Long> {

    @Query("select DISTINCT o from ActivityPost o join fetch o.file")
    List<ActivityPost> findAllJoinFetch();

}
