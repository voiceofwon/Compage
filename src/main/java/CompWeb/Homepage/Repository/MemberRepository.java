package CompWeb.Homepage.Repository;

import CompWeb.Homepage.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUsername(String username);
    List<Member> findByGrade(int grade);
    List<Member> findByUsernameContaining(String year);
    Optional<Member> deleteMemberByUsername(String username);
}
