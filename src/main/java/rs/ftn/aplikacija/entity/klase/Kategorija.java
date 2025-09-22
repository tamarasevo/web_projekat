package rs.ftn.aplikacija.entity.klase;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private Set<Transakcija> transakcije=new HashSet<>();

    //veza sa korisnikom
    @ManyToOne
    @JsonBackReference
    private Korisnik korisnik;



     public Kategorija(String naziv, Tip tip, boolean predefinisan,Korisnik korisnik) {
            this.naziv = naziv;
            this.tip = tip;
            this.predefinisan = predefinisan;
            this.korisnik = korisnik;

    }

}
