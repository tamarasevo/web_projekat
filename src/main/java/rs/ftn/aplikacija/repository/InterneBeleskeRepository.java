package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ftn.aplikacija.entity.klase.InterneBeleske;

import java.util.List;

public interface InterneBeleskeRepository extends JpaRepository<InterneBeleske, Long> {
    List<InterneBeleske> findByKorisnikID(Long korisnikID);

}