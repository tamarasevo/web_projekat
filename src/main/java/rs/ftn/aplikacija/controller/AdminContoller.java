package rs.ftn.aplikacija.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.dto.KategorijaDTO;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.service.AdminService;
import rs.ftn.aplikacija.service.KategorijaService;

@RestController
@RequestMapping("/api/admin")
public class AdminContoller {

    private final AdminService adminService;
    public AdminContoller(AdminService adminService) {
        this.adminService = adminService;
    }
    @Autowired
    private KategorijaService kategorijaService;

    //blokiranje
    @PutMapping("/blokiraj/{id}")
    public ResponseEntity<Korisnik> blokiraj(@PathVariable Long id){
        Korisnik korisnik=adminService.blokrijaKorisnika(id);
        return ResponseEntity.ok(korisnik);
    }

    //odblokiranje korisnika
    @PutMapping("/odblokiraj/{id}")
    public ResponseEntity<Korisnik> odblokiraj(@PathVariable Long id){
        Korisnik korisnik=adminService.odblokrijaKorisnika(id);
        return ResponseEntity.ok(korisnik);
    }

    @PostMapping("/dodaj-kategoriju")
    public ResponseEntity<Kategorija> dodajKategoriju(@RequestBody KategorijaDTO kategorija){
        Kategorija k=kategorijaService.dodajPredefinisanKategoriju(kategorija.getNaziv(),kategorija.getTip());
        return ResponseEntity.ok(k);
    }



}
