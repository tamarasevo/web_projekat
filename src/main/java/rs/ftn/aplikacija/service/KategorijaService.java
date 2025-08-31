package rs.ftn.aplikacija.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.repository.KategorijaRepository;
import rs.ftn.aplikacija.repository.KorisnikRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class KategorijaService {

    private final KategorijaRepository kategorijaRepository;

    @Autowired
    private  KorisnikRepository korisnikRepository;


    public KategorijaService(KategorijaRepository kategorijaRepository) {
        this.kategorijaRepository = kategorijaRepository;
    }

    public Kategorija save(Kategorija kategorija){
        return kategorijaRepository.save(kategorija);
    }

    public Kategorija getByKategorijaId(Long kategorijaId){
        return kategorijaRepository.findById(kategorijaId).orElse(null);
    }

   /* public Kategorija update(Kategorija kategorija){
        return kategorijaRepository.save(kategorija);
    }*/

    public void delete(Kategorija kategorija){
        kategorijaRepository.delete(kategorija);
    }

    //pregled svih kategorija
    public List<Kategorija> pregledSveKategorije(Long id){
        List<Kategorija> sopstvene=kategorijaRepository.findByKorisnikId(id);
        List<Kategorija> predefinisan=kategorijaRepository.findByPredefinisanTrue();
        //nAPRAVITI PROMENLJIVU GDE CEMO STAVTI I PREDEFINISAN I SOPSTVENE
        List<Kategorija> sve=new ArrayList<>();

        sve.addAll(sopstvene);
        sve.addAll(predefinisan);
        return sve;
    }

    //pregled sopstvenih kategorija
    public List<Kategorija> pregledSopstvenihKategorija(Long id){
        return kategorijaRepository.findByKorisnikId(id);
    }

    //dodavanje kategorije
    public Kategorija dodajKategoriju(Kategorija kategorija,Long korisnikId){
        Korisnik korisnik=korisnikRepository.findById(korisnikId).orElse(null);
        if(korisnik==null){
            return null;
        }
        kategorija.setKorisnik(korisnik);
        kategorija.setPredefinisan(false);
        return kategorijaRepository.save(kategorija);

    }

}
