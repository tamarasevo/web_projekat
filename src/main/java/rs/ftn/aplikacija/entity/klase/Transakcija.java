package rs.ftn.aplikacija.entity.klase;


import jakarta.persistence.*;
import rs.ftn.aplikacija.entity.klase.enums.Tip;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Transakcija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private BigDecimal iznos;
    private Tip tip;
    //DATUM STAVITI
    private boolean ponavljajuca;

    //DODATI KONSTURKOTR


    //MORAMO ZA SVE KLASE IMATI SUPROTNU POVEZANOST @OneToMany
    //1 transakcija spada u 1 kategorija, ali u 1 kategoriju
    //moze spadati vise transakcija
    @ManyToOne
    private Kategorija kategorije;

    //1 nocanik, 1 korisnik, ali korisnik ima vise novcanika
    @ManyToOne
    private Novcanik novcanik;

    //1 transakcija 1 korisnik, ali 1 korisnik moze imati vise transakcija
    @ManyToOne
    private Korisnik korisnik;

    //GETERI I SETERI
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public boolean isPonavljajuca() {
        return ponavljajuca;
    }

    public void setPonavljajuca(boolean ponavljajuca) {
        this.ponavljajuca = ponavljajuca;
    }

    public Kategorija getKategorije() {
        return kategorije;
    }

    public void setKategorije(Kategorija kategorije) {
        this.kategorije = kategorije;
    }

    public Novcanik getNovcanik() {
        return novcanik;
    }

    public void setNovcanik(Novcanik novcanik) {
        this.novcanik = novcanik;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
