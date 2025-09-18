package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Korisnik;

import java.time.LocalDate;
import java.util.List;

@Repository

//ima u sebi long count() metodu koja nam vraca broj
//redova u tabeli
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Korisnik findByKorisnickoIme(String korisnickoIme);
    @Query("SELECT COUNT(k) FROM Korisnik k WHERE k.datumPoslednjegLogovanja >= :datum")
    Long countAktivnih(@Param("datum") LocalDate datum);
    List<Korisnik> findByDatumPoslednjeAktivnostiAfter(LocalDate odDatuma);

}
