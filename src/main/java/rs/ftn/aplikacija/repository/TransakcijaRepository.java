package rs.ftn.aplikacija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ftn.aplikacija.entity.klase.Transakcija;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TransakcijaRepository extends JpaRepository<Transakcija, Long> {
   //treba nam upit da u nekom novcaniku trazi transakcije
    List<Transakcija> findByNovcanikId(Long novcanikId);
    List<Transakcija> findByPonavljajucaTrue();
   // List<Transakcija> findByNovcanikAndDatumTransakcija(Long novcanikId, LocalDate datum);

    //Dnevni pregled
    @Query("SELECT t.datumTransakcije, " +
            "SUM(CASE WHEN t.tip = 'PRIHOD' THEN t.iznos ELSE 0 END), " +
            "SUM(CASE WHEN t.tip = 'TROSAK' THEN t.iznos ELSE 0 END) " +
            "FROM Transakcija t " +
            "WHERE t.id = :korisnikId " +
            "GROUP BY t.datumTransakcije ORDER BY t.datumTransakcije")
    List<Object[]> getDnevniPrikaz(@Param("korisnikId") Long korisnikId);

    //Nedeljni pregled
    @Query("SELECT CONCAT(FUNCTION('YEAR', t.datumTransakcije), '-W', FUNCTION('WEEK', t.datumTransakcije)), " +
            "SUM(CASE WHEN t.tip = 'PRIHOD' THEN t.iznos ELSE 0 END), " +
            "SUM(CASE WHEN t.tip = 'TROSAK' THEN t.iznos ELSE 0 END) " +
            "FROM Transakcija t " +
            "WHERE t.id = :korisnikId " +
            "GROUP BY FUNCTION('YEAR', t.datumTransakcije), FUNCTION('WEEK', t.datumTransakcije) " +
            "ORDER BY FUNCTION('YEAR', t.datumTransakcije), FUNCTION('WEEK', t.datumTransakcije)")
    List<Object[]> getNedeljniPrikaz(@Param("korisnikId") Long korisnikId);

    // Mesečni pregled
    @Query("SELECT CONCAT(FUNCTION('YEAR', t.datumTransakcije), '-', FUNCTION('MONTH', t.datumTransakcije)), " +
            "SUM(CASE WHEN t.tip = 'PRIHOD' THEN t.iznos ELSE 0 END), " +
            "SUM(CASE WHEN t.tip = 'TROSAK' THEN t.iznos ELSE 0 END) " +
            "FROM Transakcija t " +
            "WHERE t.id = :korisnikId " +
            "GROUP BY FUNCTION('YEAR', t.datumTransakcije), FUNCTION('MONTH', t.datumTransakcije) " +
            "ORDER BY FUNCTION('YEAR', t.datumTransakcije), FUNCTION('MONTH', t.datumTransakcije)")
    List<Object[]> getMesecniPrikaz(@Param("korisnikId") Long korisnikId);

    // Godišnji pregled
    @Query("SELECT FUNCTION('YEAR', t.datumTransakcije), " +
            "SUM(CASE WHEN t.tip = 'PRIHOD' THEN t.iznos ELSE 0 END), " +
            "SUM(CASE WHEN t.tip = 'TROSAK' THEN t.iznos ELSE 0 END) " +
            "FROM Transakcija t " +
            "WHERE t.id = :korisnikId " +
            "GROUP BY FUNCTION('YEAR', t.datumTransakcije) " +
            "ORDER BY FUNCTION('YEAR', t.datumTransakcije)")
    List<Object[]> getGodisnjiPrikazs(@Param("korisnikId") Long korisnikId);


}
