package rs.ftn.aplikacija.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.dto.KategorijaDTO;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.repository.KorisnikRepository;
import rs.ftn.aplikacija.repository.TransakcijaRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class AdminService {

    private final KorisnikRepository korisnikrepository;
    @Autowired
    private KategorijaService kategorijaService;

    public AdminService(KorisnikRepository korisnikrepository) {
        this.korisnikrepository = korisnikrepository;
    }
    @Autowired
    private NovcanikService novcanikService;
    @Autowired
    private TransakcijaService transakcijaService;
    @Autowired
    private TransakcijaRepository transakcijaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;

    //blokiranje korisnika
    @Transactional
    public Korisnik blokrijaKorisnika(Long id){
        Korisnik korisnik=korisnikrepository.findById(id).orElse(null);
        if(korisnik==null){
            throw new RuntimeException("Korisnik ne postoji!");
        }
        korisnik.setBlokiran(true);
       return korisnikrepository.save(korisnik);
    }

    //odblokrianje korisnika
    @Transactional
    public Korisnik odblokrijaKorisnika(Long id){
        Korisnik korisnik=korisnikrepository.findById(id).orElse(null);
        if(korisnik==null){
            throw new RuntimeException("Korisnik ne postoji!");
        }
        korisnik.setBlokiran(false);
        return korisnikrepository.save(korisnik);
    }

    //pregled svih transakcija jednog korisnka
    public List<Transakcija> getSveTransakcija(Long korisnikid){
        List<Novcanik> novcanici=novcanikService.getNovcanici(korisnikid);
        List<Transakcija> transakcije=new ArrayList<>();

        for(Novcanik n: novcanici){
            List<Transakcija> t=transakcijaService.getTransakcije(n.getId());
            transakcije.addAll(t);
        }
        //sortirane po datumu
        Collections.sort(transakcije, Comparator.comparing(Transakcija::getDatumTransakcije));
        return transakcije;
    }

    public Kategorija dodajPredefinisanu(KategorijaDTO kategorija){
        Kategorija nova=kategorijaService.dodajPredefinisanKategoriju(kategorija.getNaziv(),kategorija.getTip());
        return nova;
    }

    public long getBrojKorisnika(){
        return korisnikrepository.count();
    }

    public List<Transakcija> getSveTransakcijeSvihKorisnika() {
        List<Korisnik> korisnici = korisnikrepository.findAll();
        List<Transakcija> sveTransakcije = new ArrayList<>();

        for (Korisnik korisnik : korisnici) {
            List<Novcanik> novcanici = novcanikService.getNovcanici(korisnik.getId());
            for (Novcanik n : novcanici) {
                List<Transakcija> t = transakcijaService.getTransakcije(n.getId());
                sveTransakcije.addAll(t);
            }
        }

        // Sortiranje po datumu transakcije opadajuće
        //sveTransakcije.sort(Comparator.comparing(Transakcija::getDatum, Comparator.nullsLast(Comparator.naturalOrder())));

        return sveTransakcije;
    }

    public List<Transakcija> getTransakcijePoKategoriji(Kategorija kategorija) {
        return transakcijaRepository.findByKategorija(kategorija);
    }

    public Long brojAktivnihKorisnika() {
        LocalDate preMesecDana = LocalDate.now().minusDays(30);
        return korisnikRepository.countAktivnih(preMesecDana);
    }

    public BigDecimal prosecnoStanjeAktivnihKorisnika() {
        // Aktivni korisnici u poslednjih mesec dana
        LocalDate mesecDanaUnazad = LocalDate.now().minusDays(30);
        List<Korisnik> aktivniKorisnici = korisnikRepository.findByDatumPoslednjegLogovanjaAfter(mesecDanaUnazad);

        if (aktivniKorisnici.isEmpty()) {
            return BigDecimal.ZERO; // nema aktivnih korisnika
        }

        // Sabiranje stanja svih novčanika aktivnih korisnika
        BigDecimal ukupnoStanje = aktivniKorisnici.stream()
                .flatMap(k -> k.getNovcanici().stream()) // pretpostavljamo da korisnik ima listu novčanika
                .map(Novcanik::getTrenutnoStanje)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Prosečno stanje po aktivnom korisniku
        return ukupnoStanje.divide(BigDecimal.valueOf(aktivniKorisnici.size()), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal ukupnoStanjeSvihKorisnika(){

        BigDecimal ukupno = BigDecimal.ZERO;
        List<Korisnik> sviKorisnici = korisnikRepository.findAll();
        for (Korisnik k : sviKorisnici) {
            for (Novcanik n : k.getNovcanici()) {
                    ukupno = ukupno.add(n.getTrenutnoStanje());
            }
        }
        return ukupno;
    }

    public List<Transakcija> top10TransakcijaPoslednjih30Dana() {
        LocalDate odDatuma = LocalDate.now().minusDays(30);
        Pageable top10 = PageRequest.of(0, 10);
        return transakcijaRepository.findTopTransakcija30(odDatuma, top10);
    }

    public List<Transakcija> top10TransakcijaPoslednja2Minuta() {
        LocalDateTime odDatuma = LocalDateTime.now().minusMinutes(2);
        Pageable top10 = PageRequest.of(0, 10);
        return transakcijaRepository.findTopTransakcije(odDatuma, top10);
    }



}
