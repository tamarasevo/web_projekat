package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Valuta;

@Repository
public interface ValutaRepository extends JpaRepository<Valuta, Long> {
    Valuta findByNaziv(String naziv);
}
