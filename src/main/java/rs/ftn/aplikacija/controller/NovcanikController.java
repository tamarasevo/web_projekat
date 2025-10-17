package rs.ftn.aplikacija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.config.JwtUtil;
import rs.ftn.aplikacija.dto.NovcanikDto;
import rs.ftn.aplikacija.dto.TransakcijaDto;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.service.KorisnikService;
import rs.ftn.aplikacija.service.NovcanikService;
import rs.ftn.aplikacija.service.TransakcijaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/novcanici")
public class NovcanikController {

    private final NovcanikService novcanikService;
    private final KorisnikService korisnikService;
    private final TransakcijaService transakcijaService;

    @Autowired
    public NovcanikController(NovcanikService novcanikService,
                              KorisnikService korisnikService,
                              TransakcijaService transakcijaService) {
        this.novcanikService = novcanikService;
        this.korisnikService = korisnikService;
        this.transakcijaService = transakcijaService;
    }

    // Dodavanje novčanika za ulogovanog korisnika
    @PostMapping("/dodaj")
    public ResponseEntity<Novcanik> dodajNovcanik(
            @RequestBody NovcanikDto novcanikDto,
            @CookieValue(value = "token", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        Long korisnikId = JwtUtil.getKorisnikId(token);
        if (korisnikId == null) {
            return ResponseEntity.status(401).build();
        }

        Novcanik novcanik = novcanikService.dodajNovcanik(novcanikDto, korisnikId);
        return ResponseEntity.ok(novcanik);
    }

    // Dobavljanje svih novčanika ulogovanog korisnika
    @GetMapping("/ulogovan")
    public ResponseEntity<List<Novcanik>> getNovcaniciUlogovanog(
            @CookieValue(value = "token", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        Long korisnikId = JwtUtil.getKorisnikId(token);
        if (korisnikId == null) {
            return ResponseEntity.status(401).build();
        }

        List<Novcanik> novcanici = novcanikService.getNovcaniciKorisnika(korisnikId);
        return ResponseEntity.ok(novcanici);
    }

    // Ažuriranje novčanika
    @PutMapping("/azuriraj")
    public ResponseEntity<Novcanik> azurirajNovcanik(@RequestBody NovcanikDto novcanikDto) {
        Novcanik izmenjen = novcanikService.azurirajNovcanik(novcanikDto.getNovcanikId(), novcanikDto);
        if (izmenjen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(izmenjen);
    }

    // Brisanje novčanika
    @DeleteMapping("/obrisi/{id}")
    public ResponseEntity<Void> obrisiNovcanik(@PathVariable Long id) {
        Novcanik novcanik = novcanikService.getById(id);
        if (novcanik == null) {
            return ResponseEntity.notFound().build();
        }
        novcanikService.delete(novcanik);
        return ResponseEntity.noContent().build();
    }

    // Pregled transakcija po novčaniku
    @GetMapping("/{novcanikId}/transakcije")
    public ResponseEntity<List<TransakcijaDto>> pregledTransakcija(@PathVariable Long novcanikId) {
        List<Transakcija> transakcije = transakcijaService.getTransakcije(novcanikId);
        if (transakcije.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TransakcijaDto> dtoList = transakcije.stream()
                .map(TransakcijaDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Arhiviranje novčanika
    @PutMapping("/{novcanikId}/arhiviran")
    public ResponseEntity<Boolean> arhivirajNovcanik(@PathVariable Long novcanikId) {
        boolean uspesno = novcanikService.arhiviraj(novcanikId);
        if (!uspesno) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(true);
    }

    // Dobavljanje stanja novčanika
    @GetMapping("/{novcanikId}/stanje")
    public ResponseEntity<BigDecimal> stanjeNovcanika(@PathVariable Long novcanikId) {
        BigDecimal stanje = novcanikService.getStanjeNovcanika(novcanikId);
        if (stanje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stanje);
    }

    // Ukupno stanje svih novčanika ulogovanog korisnika
    @GetMapping("/ulogovan/stanje")
    public ResponseEntity<BigDecimal> ukupnoStanjeUlogovanog(
            @CookieValue(value = "token", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        Long korisnikId = JwtUtil.getKorisnikId(token);
        if (korisnikId == null) {
            return ResponseEntity.status(401).build();
        }

        BigDecimal ukupno = novcanikService.getUkupnoStanjeKorisnika(korisnikId);
        return ResponseEntity.ok(ukupno);
    }
}

