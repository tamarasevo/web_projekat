package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ftn.aplikacija.entity.klase.Novcanik;

public interface NovcanikRepository extends JpaRepository<Novcanik, Long> {
}
