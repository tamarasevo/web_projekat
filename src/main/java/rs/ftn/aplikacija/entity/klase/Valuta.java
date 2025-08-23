package rs.ftn.aplikacija.entity.klase;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Valuta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;  // RSD, EUR, USD
    private double vrednost; // u odnosu na EUR

    public Valuta(String naziv, double vrednost) {
        this.naziv = naziv;
        this.vrednost = vrednost;

    }

    //jer imamo vec u novcaniku ManyToOne i moramo povezati
    @OneToMany(mappedBy="valuta")
    private Set<Novcanik> novcanici = new HashSet<>();
    //proveriti zasto ovo hoce da inicijaizuju u konstrukotru



    public String getNaziv() {
        return naziv;
    }

    public double getVrednost() {
        return vrednost;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setVrednost(double vrednost) {
        this.vrednost = vrednost;
    }


}

