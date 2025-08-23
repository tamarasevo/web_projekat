package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ftn.aplikacija.entity.klase.Valuta;

public interface ValutaRepository extends JpaRepository<Valuta, Long> {
}
