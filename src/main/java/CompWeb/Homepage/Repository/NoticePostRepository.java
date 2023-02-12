package CompWeb.Homepage.Repository;


import CompWeb.Homepage.Entity.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticePostRepository extends JpaRepository<NoticePost,Long> {
}
