package rs.ftn.aplikacija.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.entity.klase.enums.Ucestalost;
import rs.ftn.aplikacija.service.NovcanikService;
import rs.ftn.aplikacija.service.TransakcijaService;

//rest kontorler nam treba jer ce nam on vratiti podatke
//koje cemo koristiti u frontendu
//a kontroler bi nam vratio HTML stranicu
@RestController
public class TransakcijaController {

    private final NovcanikService novcanikService;
    private final TransakcijaService transakcijaService;

    public TransakcijaController( NovcanikService novcanikService, TransakcijaService transakcijaService) {
        this.novcanikService = novcanikService;
        this.transakcijaService = transakcijaService;
    }

    //dodaj transakciju(iznos,tip,datum,kategorija)
    @PostMapping("/dodaj")
    public ResponseEntity<Transakcija> dodajTransakcija(@RequestBody Transakcija transakcija,@PathVariable Long novcanikId){

        //prvo nadjemo novcanik kome pripada tarnsakcija
        Novcanik novcanik=novcanikService.getById(novcanikId);
        if(novcanik==null){
            return ResponseEntity.notFound().build();
        }

        //povezi transakcija sa novcanikom i povezi sa korisnikom
        transakcija.setNovcanik(novcanik);

        //trebamo da stavimo transakciju sa korisiko kome pripada novcanik
        transakcija.setKorisnik(novcanik.getKorisnik());


        //cuvamo transakciju
        Transakcija sacuvana=transakcijaService.save(transakcija);

        if(transakcija.getTip().equals("PRIHOD")){
            novcanik.setTrenutnoStanje(novcanik.getTrenutnoStanje().add(transakcija.getIznos()));
        }else
            novcanik.setTrenutnoStanje(novcanik.getTrenutnoStanje().subtract(transakcija.getIznos()));

        //update novcanik
        novcanikService.save(novcanik);

        return ResponseEntity.ok(sacuvana);
    }

    //dodavanje ponavljajuce transakcije
    //ucestalost nam je zapravo zadatak interval korinsik moze
    //birati izmedju Nedeljnog,mesecnog i godinsjeg ponavljanja
    @PutMapping("/{transakcijaId}/ponavljajuca")
    public ResponseEntity<Boolean> staviPonavljajucu(@PathVariable Long transakcijaId,@RequestParam Ucestalost ucestalost){
        Boolean uspesno=transakcijaService.ponavljajuca(transakcijaId,ucestalost);

        if(!uspesno){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(uspesno);
    }

    //iskljucivanje transakcije
    @PutMapping("/{transakcijaId}/iskljuci")
    public ResponseEntity<Boolean> iskljuciPonavljajucu(@PathVariable Long transakcijaId){
        Boolean uspesno=transakcijaService.iskljuciPonavljajuca(transakcijaId);

        if(!uspesno){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(uspesno);
    }


}
