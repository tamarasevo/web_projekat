package rs.ftn.aplikacija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.dto.KorisnikDTO;
import rs.ftn.aplikacija.dto.TransakcijaDto;
import rs.ftn.aplikacija.dto.TransferDTO;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.entity.klase.Transfer;
import rs.ftn.aplikacija.entity.klase.enums.Tip;
import rs.ftn.aplikacija.entity.klase.enums.Ucestalost;
import rs.ftn.aplikacija.service.KorisnikService;
import rs.ftn.aplikacija.service.NovcanikService;
import rs.ftn.aplikacija.service.TransakcijaService;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//rest kontorler nam treba jer ce nam on vratiti podatke
//koje cemo koristiti u frontendu
//a kontroler bi nam vratio HTML stranicu
@RestController
@RequestMapping("/api/transakcije")
public class TransakcijaController {

    private final NovcanikService novcanikService;
    private final TransakcijaService transakcijaService;

    public TransakcijaController( NovcanikService novcanikService, TransakcijaService transakcijaService) {
        this.novcanikService = novcanikService;
        this.transakcijaService = transakcijaService;
    }
    @Autowired
    private KorisnikService korisnikService;

    //dodaj transakciju(iznos,tip,datum,kategorija)
    @PostMapping("/dodaj")
    public ResponseEntity<Transakcija> dodajTransakcija(@RequestBody TransakcijaDto transakcija) {

        //prvo nadjemo novcanik kome pripada tarnsakcija

           // transakcijaDto.setNovcanikId(novcanikId); // osiguravamo da DTO ima novcanikId
            Transakcija sacuvana = transakcijaService.dodajTransakcije(transakcija,transakcija.getNovcanikId());
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

    //transfer izmedju dva novcanika
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO transfer) {

        System.out.println("Transfer"+transfer);
        transakcijaService.transferIzmedjuNovcanika(transfer.getIzNovcanika(), transfer.getAsd(), transfer.getIznos());
            return ResponseEntity.ok("Transfer uspešno izvršen");

    }

    @GetMapping("/top10troskova")
    public ResponseEntity<List<Transakcija>> top10troskova(@RequestParam LocalDate odDatuma,@RequestParam LocalDate doDatuma){
        List<Transakcija> transakcije=transakcijaService.findTopTroskovi(odDatuma,doDatuma);
        return ResponseEntity.ok(transakcije);
    }


    //pregled za korisnika njegovi dnevni mesecni nedeljni i godisnji troskovi
    @GetMapping("/dnevni")
    public List<Transakcija> dnevniPregled(
            @RequestParam String datum,                  // yyyy-MM-dd
            @RequestParam(required = false) String tip,   // PRIHOD / TROSAK / null = oba
            Authentication authentication
    ) {
        String username = authentication.getName();
        Korisnik korisnik = korisnikService.getKorisnickoIme(username);
        Long korisnikId = korisnik.getId();

        LocalDate localDate = LocalDate.parse(datum);

        if(tip == null) {
            List<Transakcija> prihodi = transakcijaService.dnevniPrihodi(korisnikId, localDate);
            List<Transakcija> troskovi = transakcijaService.dnevniTroskovi(korisnikId, localDate);
            prihodi.addAll(troskovi);
            return prihodi;
        }

        return tip.equalsIgnoreCase("PRIHOD")
                ? transakcijaService.dnevniPrihodi(korisnikId, localDate)
                : transakcijaService.dnevniTroskovi(korisnikId, localDate);
    }

    @GetMapping("/nedeljni")
    public List<Transakcija> nedeljniPregled(
            @RequestParam String start,
            @RequestParam String end,// yyyy-MM-dd
            @RequestParam(required = false) String tip,   // PRIHOD / TROSAK / null = oba
            Authentication authentication
    ) {
        String username = authentication.getName();
        Korisnik korisnik = korisnikService.getKorisnickoIme(username);
        Long korisnikId = korisnik.getId();

        LocalDate localDate1 = LocalDate.parse(start);
        LocalDate localDate2 = LocalDate.parse(end);

        if(tip == null) {
            List<Transakcija> prihodi = transakcijaService.nedeljniPrihodi(korisnikId, localDate1, localDate2);
            List<Transakcija> troskovi = transakcijaService.nedeljniTroskovi(korisnikId, localDate1,localDate2);
            prihodi.addAll(troskovi);
            return prihodi;
        }

        return tip.equalsIgnoreCase("PRIHOD")
                ? transakcijaService.nedeljniPrihodi(korisnikId, localDate1,localDate2)
                : transakcijaService.nedeljniTroskovi(korisnikId, localDate1,localDate2);
    }

    @GetMapping("/mesecni")
    public List<Transakcija> mesecniPregled(
            @RequestParam String start,
            @RequestParam String end,// yyyy-MM-dd
            @RequestParam(required = false) String tip,   // PRIHOD / TROSAK / null = oba
            Authentication authentication
    ) {
        String username = authentication.getName();
        Korisnik korisnik = korisnikService.getKorisnickoIme(username);
        Long korisnikId = korisnik.getId();

        LocalDate localDate1 = LocalDate.parse(start);
        LocalDate localDate2 = LocalDate.parse(end);

        if(tip == null) {
            List<Transakcija> prihodi = transakcijaService.mesecniPrihodi(korisnikId, localDate1, localDate2);
            List<Transakcija> troskovi = transakcijaService.mesecniTroskovi(korisnikId, localDate1,localDate2);
            prihodi.addAll(troskovi);
            return prihodi;
        }

        return tip.equalsIgnoreCase("PRIHOD")
                ? transakcijaService.mesecniPrihodi(korisnikId, localDate1,localDate2)
                : transakcijaService.mesecniTroskovi(korisnikId, localDate1,localDate2);
    }

    @GetMapping("/godisnji")
    public List<Transakcija> godisnjiPregled(
            @RequestParam String start,
            @RequestParam String end,// yyyy-MM-dd
            @RequestParam(required = false) String tip,   // PRIHOD / TROSAK / null = oba
            Authentication authentication
    ) {
        String username = authentication.getName();
        Korisnik korisnik = korisnikService.getKorisnickoIme(username);
        Long korisnikId = korisnik.getId();

        LocalDate localDate1 = LocalDate.parse(start);
        LocalDate localDate2 = LocalDate.parse(end);

        if(tip == null) {
            List<Transakcija> prihodi = transakcijaService.godisnjiPrihodi(korisnikId, localDate1, localDate2);
            List<Transakcija> troskovi = transakcijaService.godisnjiTroskovi(korisnikId, localDate1,localDate2);
            prihodi.addAll(troskovi);
            return prihodi;
        }

        return tip.equalsIgnoreCase("PRIHOD")
                ? transakcijaService.godisnjiPrihodi(korisnikId, localDate1,localDate2)
                : transakcijaService.godisnjiTroskovi(korisnikId, localDate1,localDate2);
    }





}
