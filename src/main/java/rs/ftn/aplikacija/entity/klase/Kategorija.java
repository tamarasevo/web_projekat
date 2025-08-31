package rs.ftn.aplikacija.entity.klase;

import jakarta.persistence.*;
import rs.ftn.aplikacija.entity.klase.enums.Tip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kategorija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String naziv;

    @Enumerated(EnumType.STRING)
    private Tip tip;

    private boolean predefinisan; // da li je podrazumevana ili mora rucno od svakog korisnika


    //veza sa transakcijama
    @OneToMany(mappedBy="kategorije", cascade = CascadeType.ALL)
    private Set<Transakcija> transakcije=new HashSet<>();

    //veza sa korisnikom
    @ManyToOne
    private Korisnik korisnik;



     public Kategorija(String naziv, Tip tip, boolean predefinisan,Korisnik korisnik) {
            this.naziv = naziv;
            this.tip = tip;
            this.predefinisan = predefinisan;
            this.korisnik = korisnik;

    }

    /*public Kategorija() {

    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public boolean isPredefinisan() {
        return predefinisan;
    }

    public void setPredefinisan(boolean predefinisan) {
        this.predefinisan = predefinisan;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }*/
}
