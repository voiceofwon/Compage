package CompWeb.Homepage.Repository;

import CompWeb.Homepage.Entity.SosPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SosPostRepository extends JpaRepository<SosPost,Long> {

    @Query("select DISTINCT e from SosPost e join fetch e.file")
    List<SosPost> findAllJoinFetch();
}
