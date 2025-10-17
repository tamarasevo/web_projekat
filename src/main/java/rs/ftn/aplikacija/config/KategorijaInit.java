package rs.ftn.aplikacija.config;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.entity.klase.enums.Tip;
import rs.ftn.aplikacija.repository.KategorijaRepository;

import java.util.List;


//ovo commandLineRunner znaci da ce se ovo sa pokretanjem aplikacije
//napraviti odredjene kategorije
@Component
public class KategorijaInit implements CommandLineRunner {
    private final KategorijaRepository kategorijaRepository;

    public KategorijaInit(KategorijaRepository kategorijaRepository) {
        this.kategorijaRepository = kategorijaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Proveravamo da li globalne kategorije već postoje
        if (kategorijaRepository.countByPredefinisanTrue() == 0) {
            Kategorija hrana = new Kategorija();
            hrana.setNaziv("Hrana");
            hrana.setTip(Tip.TROSAK);
            hrana.setPredefinisan(true);
            hrana.setAktivna(true);

            Kategorija zabava = new Kategorija();
            zabava.setNaziv("Zabava");
            zabava.setTip(Tip.TROSAK);
            zabava.setPredefinisan(true);
            zabava.setAktivna(true);

            Kategorija domacinstvo = new Kategorija();
            domacinstvo.setNaziv("Domacinstvo");
            domacinstvo.setTip(Tip.TROSAK);
            domacinstvo.setPredefinisan(true);
            domacinstvo.setAktivna(true);

            Kategorija investicije = new Kategorija();
            investicije.setNaziv("Investicije");
            investicije.setTip(Tip.TROSAK);
            investicije.setPredefinisan(true);
            investicije.setAktivna(true);

            kategorijaRepository.saveAll(List.of(hrana, zabava, domacinstvo, investicije));
            System.out.println("Globalne kategorije su kreirane.");
        }
    }
}
