package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Transakcija;

import java.util.List;


@Repository
public interface TransakcijaRepository extends JpaRepository<Transakcija, Long> {
   //treba nam upit da u nekom novcaniku trazi transakcije
    List<Transakcija> findByNovcanikId(Long novcanikId);
    List<Transakcija> findByPonavljajucaTrue();
}
