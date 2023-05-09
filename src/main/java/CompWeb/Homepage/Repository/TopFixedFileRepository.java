package CompWeb.Homepage.Repository;


import CompWeb.Homepage.Entity.TopFixedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopFixedFileRepository extends JpaRepository<TopFixedFile,Long> {
}
