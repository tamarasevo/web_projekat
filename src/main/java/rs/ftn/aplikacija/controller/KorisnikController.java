package rs.ftn.aplikacija.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.dto.LoginDto;
import rs.ftn.aplikacija.dto.RegistracijaDto;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.service.KorisnikService;

@CrossOrigin
@RestController
@RequestMapping("/api/korisnici")
public class KorisnikController {

    private KorisnikService korisnikService;

    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    //Vraca broj korisnika
    //stavka 1.
    @GetMapping("/count")
    public ResponseEntity<Long> getBrojKorisnika() {
        long broj= korisnikService.brojKorisnika();
        return ResponseEntity.ok(broj);
    }
    @PutMapping("/profil/{korisnikId}")
    public ResponseEntity<Korisnik> izmeniProfil(@PathVariable Long korisnikId,@RequestBody Korisnik korisnik) {
        Korisnik izmenjen=korisnikService.izmeniKorisnika(korisnikId,korisnik);
        if(izmenjen!=null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(izmenjen);
    }

    //registracija korisnika
   @PostMapping("/registracija")
    public ResponseEntity<Korisnik> registracija(@RequestBody RegistracijaDto korisnik) {
        Korisnik sacuvan=korisnikService.registrujKorisnika(korisnik);

        if(sacuvan==null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(sacuvan);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try{
            Korisnik korisnik=korisnikService.login(loginDto.getKorisnickoIme(),loginDto.getLozinka());
            return ResponseEntity.ok(korisnik);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
