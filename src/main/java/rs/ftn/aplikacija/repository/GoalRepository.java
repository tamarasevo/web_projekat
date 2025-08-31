package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Goal;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
}
