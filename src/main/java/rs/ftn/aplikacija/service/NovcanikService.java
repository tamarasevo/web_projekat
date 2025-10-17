package rs.ftn.aplikacija.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.dto.NovcanikDto;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Valuta;
import rs.ftn.aplikacija.repository.NovcanikRepository;
import rs.ftn.aplikacija.repository.ValutaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class NovcanikService {

    private final NovcanikRepository novcanikRepository;
    private final KorisnikService korisnikService;
    @Autowired
    private ValutaRepository valutaRepository;
   /* @Autowired
    private FrankfurtApiService FAS ;*/
    @Autowired
    private ValutaService valutaService;

    public NovcanikService(NovcanikRepository novcanikRepository, KorisnikService korisnikService) {
        this.novcanikRepository = novcanikRepository;
        this.korisnikService = korisnikService;
    }

    /*public Novcanik getById(Long id){
        return novcanikRepository.findById(id).orElseThrow(() -> new RuntimeException("Novcanik sa id " + id + " ne postoji"));

    }*/
    public Novcanik getById(Long id) {
        return novcanikRepository.findById(id).orElse(null);
    }

    public List<Novcanik> getNovcaniciKorisnika(Long id) {
        return novcanikRepository.findByKorisnikId(id);
    }


    public Novcanik save(Novcanik novcanik){
        return novcanikRepository.save(novcanik);
    }

    public void delete(Novcanik novcanik){
       novcanikRepository.delete(novcanik);
    }

    public List<Novcanik> getNovcanici(Long id){
        return novcanikRepository.findByKorisnikId(id);
    }

    public Boolean arhiviraj(Long novcanikId){
        Novcanik novcanik=novcanikRepository.findById(novcanikId).orElse(null);
        if(novcanik==null)
            return false;

        novcanik.setArhiviran(true);
        novcanikRepository.save(novcanik);
        return true;
    }

    public Novcanik dodajNovcanik(NovcanikDto novcanik, Long id){
        Korisnik korisnik=korisnikService.getKorisnika(id);
        if(korisnik==null){
            throw new IllegalStateException("Korisnik ne postoji korisnika!");
        }
        Valuta valuta = valutaService.getValuta(novcanik.getValuta());

        Novcanik novi=new Novcanik();
        novi.setNaziv(novcanik.getNaziv());
        novi.setKorisnik(korisnik);
        novi.setPocetnoStanje(novcanik.getPocetnoStanje());
        novi.setTrenutnoStanje(novcanik.getPocetnoStanje());
        novi.setStedni(novcanik.isStedni());
        novi.setValuta(valuta);
        novcanikRepository.save(novi);
        return novi;
    }

    //dobavljanje ukupnog stanja svih novcanika
    //tj ukupno koliko paa ima korisnik u svim novcanicima
    public BigDecimal getUkupnoStanjeKorisnika(Long korisnikId){
        List<Novcanik> novcanici=novcanikRepository.findByKorisnikId(korisnikId);
        if(novcanici.isEmpty())
            return null;
        BigDecimal suma=new BigDecimal(0);

        for(Novcanik n:novcanici){
            if(n.isArhiviran()){
                continue;
            }

            BigDecimal stanje= n.getTrenutnoStanje();
            String valuta = n.getValuta().getNaziv();

            // konvertuj u RSD samo ako nije dinar
            if (!"RSD".equalsIgnoreCase(valuta)) {
                stanje = valutaService.konvertuj(valuta, "RSD", stanje);
            }
            suma=suma.add(stanje);
        }
        return suma;
    }
    //ispravak azuriranja novcanika
    public Novcanik azurirajNovcanik(Long id, NovcanikDto novcanik) {
        Novcanik novcanik1 = getById(id);
        if (novcanik == null) {
            return null;
        }

        novcanik1.setNaziv(novcanik.getNaziv());
        novcanik1.setTrenutnoStanje(novcanik.getPocetnoStanje());
        //novcanik1.setPocetnoStanje(novcanik.getPocetnoStanje());
        novcanik1.setStedni(novcanik.isStedni());
        novcanik1.setStedni(novcanik.isStedni());

        return save(novcanik1);
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
