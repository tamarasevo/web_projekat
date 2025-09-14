package rs.ftn.aplikacija.entity.klase;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonBackReference
    private Korisnik korisnik;

    @OneToMany(mappedBy="novcanik", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transakcija> transakcije=new HashSet<>();

    //ovo am ne treba jer imam jedan cilj po novcaniku
    //videti da li da bude set ili lista
   /* @OneToMany(mappedBy = "novcanik")
    private Set<Goal> stednja=new HashSet<>();*/


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


}
