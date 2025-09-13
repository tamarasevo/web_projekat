package rs.ftn.aplikacija.entity.klase;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ftn.aplikacija.entity.klase.enums.Tip;
import rs.ftn.aplikacija.entity.klase.enums.Ucestalost;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transakcija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private BigDecimal iznos;
    @Enumerated(EnumType.STRING)
    private Tip tip;
    private LocalDate datumTransakcije;
    //po defaultu mora da bude false
    private boolean ponavljajuca=false;

    //UCESTALOST STAVICEMO KAO ENUM
    @Enumerated(EnumType.STRING)
    private Ucestalost ucestalost;


    //MORAMO ZA SVE KLASE IMATI SUPROTNU POVEZANOST @OneToMany
    //1 transakcija spada u 1 kategorija, ali u 1 kategoriju
    //moze spadati vise transakcija
    @ManyToOne
    private Kategorija kategorije;

    //1 nocanik, 1 korisnik, ali korisnik ima vise novcanika
    @ManyToOne
    private Novcanik novcanik;

    //razmisliti da li ukloniti
    //1 transakcija 1 korisnik, ali 1 korisnik moze imati vise transakcija
    @ManyToOne
    private Korisnik korisnik;

    public Transakcija(String naziv, BigDecimal iznos, Tip tip,boolean ponavljajuca,LocalDate datumTransakcije,Ucestalost ucestalost) {
        this.naziv = naziv;
        this.iznos = iznos;
        this.tip = tip;
        this.datumTransakcije = LocalDate.now();
        this.ponavljajuca = ponavljajuca;
        this.ucestalost = ucestalost;
    }


}
