package rs.ftn.aplikacija.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.repository.KorisnikRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AdminService {

    private final KorisnikRepository korisnikrepository;
    public AdminService(KorisnikRepository korisnikrepository) {
        this.korisnikrepository = korisnikrepository;
    }
    @Autowired
    private NovcanikService novcanikService;
    @Autowired
    private TransakcijaService transakcijaService;

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


}
