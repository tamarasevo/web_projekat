package rs.ftn.aplikacija.service;

import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.repository.NovcanikRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class NovcanikService {

    private final NovcanikRepository novcanikRepository;

    public NovcanikService(NovcanikRepository novcanikRepository) {
        this.novcanikRepository = novcanikRepository;
    }

    public Novcanik getById(Long id){
        return novcanikRepository.findById(id).orElse(null);
    }

    public Novcanik save(Novcanik novcanik){
        return novcanikRepository.save(novcanik);
    }

    public void delete(Novcanik novcanik){
       novcanikRepository.delete(novcanik);
    }

    public Boolean arhiviraj(Long novcanikId){
        Novcanik novcanik=novcanikRepository.findById(novcanikId).orElse(null);
        if(novcanik==null)
            return false;

        novcanik.setArhiviran(true);
        novcanikRepository.save(novcanik);
        return true;
    }

    //dobavljanje ukupnog stanja svih novcanika
    //tj ukupno koliko paa ima korisnik u svim novcanicima
    public BigDecimal getUkupnoStanjeKorisnika(Long korisnikId){
        List<Novcanik> novcanici=novcanikRepository.findByKorisnikId(korisnikId);
        if(novcanici.isEmpty())
            return null;
        BigDecimal suma=new BigDecimal(0);

        for(Novcanik n:novcanici){
            suma=suma.add(n.getTrenutnoStanje());
        }
        return suma;
    }

    //daje samo stanje jednog novcanika
    public BigDecimal getStanjeNovcanika(Long novcanikId){
        Novcanik novcanik=novcanikRepository.findById(novcanikId).orElse(null);
        //bolje da nam bude 0, ako ne nadje novcanik
        //stanje je onda 0
        if(novcanik==null)
            return BigDecimal.ZERO;

        return novcanik.getTrenutnoStanje();
    }

    //opcija da se napravi novcanik za stednju


    //DODATI TRANSAKCIJE IZMEDJU NOVCANIKA KAKO BI MOGLI DA IMPLEMTIRAMO
    //2.5 DO KRAJA

}
