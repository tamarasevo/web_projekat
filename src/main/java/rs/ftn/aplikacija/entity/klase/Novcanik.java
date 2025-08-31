package rs.ftn.aplikacija.entity.klase;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Novcanik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private BigDecimal pocetnoStanje;
    private BigDecimal trenutnoStanje;
    private LocalDate datumKreiranja;
    private boolean stedni;
    //po defalutu mora biti false
    private boolean arhiviran=false;

    //veza, jedna valuta moze biti u vise novcanika
    @ManyToOne
    private Valuta valuta;

    //jedan korisnik moze imati vise novcanika
    @ManyToOne
    private Korisnik korisnik;

    @OneToMany(mappedBy="novcanik")
    private Set<Transakcija> transakcije=new HashSet<>();

    //videti da li da bude set ili lista
    @OneToMany(mappedBy = "novcanik")
    private Set<Goal> stednja=new HashSet<>();


     public Novcanik(String naziv, BigDecimal pocetnoStanje,BigDecimal trenutnoStanje,Valuta valuta, LocalDate datumKreiranja,
                    Korisnik korisnik, boolean stedni, boolean arhiviran) {
        this.naziv = naziv;
        this.pocetnoStanje = pocetnoStanje;
        this.trenutnoStanje = trenutnoStanje;
        this.valuta = valuta;
        this.datumKreiranja = datumKreiranja;
        this.korisnik = korisnik;
        this.stedni = stedni;
        this.arhiviran = arhiviran;
    }

 /*   public Novcanik() {

    }

   public void azurirajStanje(double iznos) {
        this.trenutnoStanje += iznos;
    }

    public String getNaziv() {
        return naziv;
    }

    public double getPocetnoStanje() {
        return pocetnoStanje;
    }

    public double getTrenutnoStanje() {
        return trenutnoStanje;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public LocalDate getDatumKreiranja() {
        return datumKreiranja;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public boolean isStedni() {
        return stedni;
    }

    public boolean isArhiviran() {
        return arhiviran;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setPocetnoStanje(double pocetnoStanje) {
        this.pocetnoStanje = pocetnoStanje;
    }

    public void setTrenutnoStanje(double trenutnoStanje) {
        this.trenutnoStanje = trenutnoStanje;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public void setDatumKreiranja(LocalDate datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public void setStedni(boolean stedni) {
        this.stedni = stedni;
    }

    public void setArhiviran(boolean arhiviran) {
        this.arhiviran = arhiviran;
    }*/
}
