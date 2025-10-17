package rs.ftn.aplikacija.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.dto.KategorijaDTO;
import rs.ftn.aplikacija.dto.ValutaDto;
import rs.ftn.aplikacija.entity.klase.InterneBeleske;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.service.AdminService;
import rs.ftn.aplikacija.service.InterneBeleskeService;
import rs.ftn.aplikacija.service.KategorijaService;
import rs.ftn.aplikacija.service.ValutaService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminContoller {

    private final AdminService adminService;
    public AdminContoller(AdminService adminService) {
        this.adminService = adminService;
    }
    @Autowired
    private KategorijaService kategorijaService;
    @Autowired
    private InterneBeleskeService beleskeService;
    @Autowired
    private ValutaService valutaService;

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

   // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/dodaj-kategoriju")
    public ResponseEntity<Kategorija> dodajKategoriju(@RequestBody KategorijaDTO kategorija){
        Kategorija k= adminService.dodajPredefinisanu(kategorija);
        return ResponseEntity.ok(k);
    }

    @GetMapping("/transakcijeSvihKorisnika")
    public ResponseEntity<List<Transakcija>> sveTransakcijeSvihKorisnika(){
        List<Transakcija> transakcije=adminService.getSveTransakcijeSvihKorisnika();
        return ResponseEntity.ok(transakcije);
    }

    @GetMapping("/transakcije/kategorija")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Transakcija>> getTransakcijePoKategoriji(
            @RequestParam Long kategorijaId
    ) {
        Kategorija kategorija = new Kategorija();
        kategorija.setId(kategorijaId);

        List<Transakcija> transakcije = adminService.getTransakcijePoKategoriji(kategorija);
        return ResponseEntity.ok(transakcije);
    }

    @GetMapping("/aktivniKorisnici")
    public ResponseEntity<Long> getBrojAktivnihKorisnika() {
        Long broj = adminService.brojAktivnihKorisnika();
        return ResponseEntity.ok(broj);
    }

    @GetMapping("/admin/prosecno-stanje-aktivnih")
    public BigDecimal getProsecnoStanjeAktivnihKorisnika() {
        return adminService.prosecnoStanjeAktivnihKorisnika();
    }

    @GetMapping("/admin/ukupno-stanje")
    public BigDecimal getUkupnoStanjeSvihKorisnika() {
        return adminService.ukupnoStanjeSvihKorisnika();
    }

    @GetMapping("/admin/top10Transakcija")
    public ResponseEntity<List<Transakcija>> top10Transakcija() {
        List<Transakcija> top10 = adminService.top10TransakcijaPoslednjih30Dana();
        return ResponseEntity.ok(top10);
    }

    @GetMapping("/admin/top10TransakcijaTest")
    public ResponseEntity<List<Transakcija>> top10TransakcijaTest() {
        List<Transakcija> top10 = adminService.top10TransakcijaPoslednja2Minuta();
        return ResponseEntity.ok(top10);
    }

    @PostMapping("/beleske")
    public ResponseEntity<InterneBeleske> dodajBelesku(@RequestParam Long korisnikId, @RequestParam String sadrzaj) {
        InterneBeleske beleska = beleskeService.dodajBelesku(korisnikId, sadrzaj);
        return ResponseEntity.ok(beleska);
    }

    @PostMapping("/dodajiliIzmeni/valutu")
   // @PreAuthorize("hasRole('ADMIN')") // samo admin može
    public ResponseEntity<String> dodajIliIzmeniValutu(@RequestBody ValutaDto dto) {
        try {
            valutaService.dodajIliIzmeniValutu(dto.getNaziv(), dto.getVrednost());
            return ResponseEntity.ok("Valuta uspešno dodata/izmenjena");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/dodaj/kategoriju")
    public ResponseEntity<String> dodajGlobalnuKategoriju(@RequestBody KategorijaDTO dto) {
        kategorijaService.dodajPredefinisanKategoriju(dto.getNaziv(), dto.getTip(), dto.isAktivna());
        return ResponseEntity.ok("Globalna kategorija dodata");
    }

    @PutMapping("/deaktiviraj/{id}")
    public ResponseEntity<String> deaktivirajGlobalnuKategoriju(@PathVariable Long id) {
        kategorijaService.deaktivirajGlobalnuKategoriju(id);
        return ResponseEntity.ok("Globalna kategorija deaktivirana");
    }






}
