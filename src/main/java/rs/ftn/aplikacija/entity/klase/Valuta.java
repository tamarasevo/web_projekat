package rs.ftn.aplikacija.entity.klase;

import jakarta.persistence.*;
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
public class Valuta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;  // RSD, EUR, USD
    private double vrednost; // u odnosu na EUR

    //jer imamo vec u novcaniku ManyToOne i moramo povezati
    @OneToMany(mappedBy="valuta")
    private Set<Novcanik> novcanici = new HashSet<>();
    //proveriti zasto ovo hoce da inicijaizuju u konstrukotru

   public Valuta(String naziv, double vrednost) {
        this.naziv = naziv;
        this.vrednost = vrednost;
    }



}

