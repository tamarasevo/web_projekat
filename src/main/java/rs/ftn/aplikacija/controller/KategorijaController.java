package rs.ftn.aplikacija.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.service.KategorijaService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KategorijaController {

    private final KategorijaService kategorijaService;

    public KategorijaController(KategorijaService kategorijaService) {
        this.kategorijaService = kategorijaService;
    }

    //pregledh svih kategorija
    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<List<Kategorija>>pregledSvihKategorija(@PathVariable Long korisnikId){
        List<Kategorija> pregledSvihKategorija = kategorijaService.pregledSveKategorije(korisnikId);
        return ResponseEntity.ok(pregledSvihKategorija);
    }

    //pregled sopstvenih kategorija
    @GetMapping("/korisnik/{korisnikId}/sopstvene}")
    public ResponseEntity<List<Kategorija>>pregledSopstvenihKategorija(@PathVariable Long korisnikId){
        List<Kategorija> sopstveneKategorije=kategorijaService.pregledSopstvenihKategorija(korisnikId);
        return ResponseEntity.ok(sopstveneKategorije);
    }

    //dodavanje kategorije
    @PostMapping("/korisnik/{korisnikId}/dodaj")
    public ResponseEntity<Kategorija> dodajKategoriju(@PathVariable Long korisnikId,@RequestBody Kategorija kategorija){
        Kategorija nova=kategorijaService.dodajKategoriju(kategorija,korisnikId);
        return ResponseEntity.ok(nova);
    }

    //brisanje kategorija
    @DeleteMapping("/{kategorijaId}/obrisi")
    public ResponseEntity<Kategorija> obrisi(@PathVariable Long kategorijaId){
        Kategorija obrisi=kategorijaService.getByKategorijaId(kategorijaId);
        if(obrisi==null){
            return ResponseEntity.notFound().build();
        }
        kategorijaService.delete(obrisi);
        return ResponseEntity.noContent().build();

    }

}
