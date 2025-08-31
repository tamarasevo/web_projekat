package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Novcanik;

import java.util.List;

@Repository
public interface NovcanikRepository extends JpaRepository<Novcanik, Long> {
    List<Novcanik> findByKorisnikId(Long id);
}
