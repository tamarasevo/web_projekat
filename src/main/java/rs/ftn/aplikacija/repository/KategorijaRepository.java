package rs.ftn.aplikacija.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Kategorija;

import java.util.List;

@Repository
public interface KategorijaRepository extends JpaRepository<Kategorija, Long> {
   //vraca nam listu kategorije, uz pomoc ovog imamo pregled svih kategorija

    List<Kategorija> findByKorisnikId(long korisnikId);
    List<Kategorija> findByPredefinisanTrue();

    long countByPredefinisanTrue();

}