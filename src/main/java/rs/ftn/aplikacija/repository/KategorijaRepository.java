package rs.ftn.aplikacija.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ftn.aplikacija.entity.klase.Kategorija;

public interface KategorijaRepository extends JpaRepository<Kategorija, Long> {
}
