package rs.ftn.aplikacija.controller;

import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.dto.NovcanikDto;
import rs.ftn.aplikacija.dto.TransakcijaDto;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.repository.KorisnikRepository;
import rs.ftn.aplikacija.service.KorisnikService;
import rs.ftn.aplikacija.service.NovcanikService;
import rs.ftn.aplikacija.service.TransakcijaService;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/novcanici")
public class NovcanikController {

    private final NovcanikService novcanikService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TransakcijaService transakcijaService;

    public NovcanikController(NovcanikService novcanikService) {
        this.novcanikService =novcanikService;
    }

    //dodavanje novcanika
    @PostMapping("{id}/dodaj")
    public ResponseEntity<Novcanik> dodajNovcanik(@RequestBody NovcanikDto novcanik,@PathVariable Long id) {

            Novcanik sacuvan = novcanikService.dodajNovcanik(novcanik,id);
            return ResponseEntity.ok(sacuvan);

    }

    //Azuriranje novcanika
    @PutMapping("/{id}")
    public ResponseEntity<Novcanik> izmeniNovcanik(
            @PathVariable Long id,
            @RequestBody NovcanikDto novcanikDto) {

        Novcanik izmenjen = novcanikService.azurirajNovcanik(id, novcanikDto);

        if (izmenjen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(izmenjen);
    }


    //Brisanje novcanika
    @DeleteMapping("/obrisi/{id}")
    public ResponseEntity<Novcanik> obrisiNovcanik(@PathVariable Long id){
        Novcanik novcanik=novcanikService.getById(id);
        if(novcanik==null)
            return ResponseEntity.notFound().build();

        novcanikService.delete(novcanik);

        //vracamo informaciju o tome da li je obrisano ili ne
        //ne koirstimo ok jer ce nam on vratiti informacije
        //o novcaniku
        return ResponseEntity.noContent().build();
    }

    //pregled transakcija po novcaniku
    @GetMapping("/{novcanikId}/transakcije")
    public ResponseEntity<List<TransakcijaDto>> pregledTransakcija(@PathVariable Long novcanikId){
        List<Transakcija> transakcije=transakcijaService.getTransakcije(novcanikId);
        if(transakcije.isEmpty())
            return ResponseEntity.noContent().build();
        List<TransakcijaDto> dtoList = transakcije.stream()
                .map(TransakcijaDto::new)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    //ovde samo pozivamao metodu za arhiviranje ne moze ovde menjati
    //contorller samo prima i prosledjuje zahteve servisu
    //ne treba nam dodatna logika tu
    @PutMapping("/{novcanikId}/arhiviran")
    public ResponseEntity<Boolean> arhivirajNovcanik(@PathVariable Long novcanikId){
        Boolean uspesno=novcanikService.arhiviraj(novcanikId);
        if(uspesno==false)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(true);
    }

    //dobavlja stanje novcanika
    @GetMapping("/{novcanikId}/stanje")
    public ResponseEntity<BigDecimal> stanjeNovcanika(@PathVariable Long novcanikId){
        BigDecimal stanje=novcanikService.getStanjeNovcanika(novcanikId);
        if(stanje==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(stanje);
    }

    //ukupno stanje
    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<BigDecimal> getUkupnoStanje(@PathVariable Long korisnikId){
        BigDecimal ukupno=novcanikService.getUkupnoStanjeKorisnika(korisnikId);
        return ResponseEntity.ok(ukupno);
    }
}
