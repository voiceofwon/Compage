package CompWeb.Homepage.Repository;


import CompWeb.Homepage.Entity.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticePostRepository extends JpaRepository<NoticePost,Long> {
    @Query("select DISTINCT o from NoticePost o join fetch o.noticeFile")
    List<NoticePost> findAllJoinFetch();
}
