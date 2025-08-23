package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ftn.aplikacija.entity.klase.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
}
