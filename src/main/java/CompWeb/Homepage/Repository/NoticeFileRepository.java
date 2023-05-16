package CompWeb.Homepage.Repository;


import CompWeb.Homepage.Entity.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeFileRepository extends JpaRepository<NoticeFile,Long> {

}
