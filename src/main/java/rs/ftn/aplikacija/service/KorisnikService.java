package rs.ftn.aplikacija.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.entity.klase.enums.Tip;
import rs.ftn.aplikacija.repository.KategorijaRepository;
import rs.ftn.aplikacija.repository.KorisnikRepository;

import java.util.List;

@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    @Autowired
    private KategorijaRepository kategorijaRepository;

    public KorisnikService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    //pozivamo za brojanje korisnika
    //iz repository pozivamo count()
    public long brojKorisnika() {
        return korisnikRepository.count();
    }

    //treba nam i za getKorisnika da vidimo da
    //li korisnik vec ima
    public Korisnik getKorisnika(Long id){
        return korisnikRepository.findById(id).orElse(null);
    }

    //izmena licnih podataka
    //azuriranje
    public Korisnik izmeniKorisnika(Long id,Korisnik pocetni){
        Korisnik  korisnik=korisnikRepository.findById(id).orElse(null);
        if(korisnik==null){
            return null;
        }

        //ovo cemo da osigurmo ako korisnik ne unese sva polja
        //da izmeni samo jedno polje,dva ili sva
        //a ostala ce da ostanu ista
        if(pocetni.getIme() != null){
            korisnik.setIme(pocetni.getIme());
        }
        if(pocetni.getPrezime()!=null){
            korisnik.setPrezime(pocetni.getPrezime());
        }
        if(pocetni.getMejl()!=null){
            korisnik.setMejl(pocetni.getMejl());
        }
        if(pocetni.getLozinkaHash()!=null){
            korisnik.setLozinkaHash(pocetni.getLozinkaHash());
        }
        if(pocetni.getProfilnaSlika()!=null){
            korisnik.setProfilnaSlika(pocetni.getProfilnaSlika());
        }
        if(pocetni.getDatumRodjenja()!=null){
            korisnik.setDatumRodjenja(pocetni.getDatumRodjenja());
        }

        return korisnikRepository.save(korisnik);

    }

    //Registracija korisnika
    public Korisnik registrujKorisnika(Korisnik korisnik){
        Korisnik sacuvan=korisnikRepository.save(korisnik);
        List<Kategorija> kategorijePredefinisane= List.of(
                new Kategorija("Hrana", Tip.TROSAK,true,sacuvan),
                new Kategorija("Domacinstvo",Tip.TROSAK,true,sacuvan),
                new Kategorija("Zabava",Tip.TROSAK,true,sacuvan),
                new Kategorija("Investicije",Tip.TROSAK,true,sacuvan));

        //snimimo sve predefinisane u bazu
        for(Kategorija k:kategorijePredefinisane){
            kategorijaRepository.save(k);
        }

        return sacuvan;
    }
}
