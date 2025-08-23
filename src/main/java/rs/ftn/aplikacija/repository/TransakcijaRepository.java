package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ftn.aplikacija.entity.klase.Transakcija;

public interface TransakcijaRepository extends JpaRepository<Transakcija, Long> {
}
