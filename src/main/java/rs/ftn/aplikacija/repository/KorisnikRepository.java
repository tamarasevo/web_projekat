package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Korisnik;

@Repository

//ima u sebi long count() metodu koja nam vraca broj
//redova u tabeli
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
}
