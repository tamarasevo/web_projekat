package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Goal;
import rs.ftn.aplikacija.entity.klase.Novcanik;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
   //ovo ce nam vratiti jedan cilj po novcaniku
    Goal findByNovcanik(Novcanik novcanik);
}
