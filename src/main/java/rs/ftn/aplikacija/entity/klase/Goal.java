package rs.ftn.aplikacija.entity.klase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private BigDecimal ciljIznos;
    private BigDecimal trenutnoStanje;
    private LocalDate krajnjiRok;

    //Vise ciljeva moze biti u jednom novcaniku
    @ManyToOne
    private Novcanik novcanik;

    @ManyToOne
    private Korisnik korisnik;

    public Goal(String naziv,BigDecimal ciljIznos,BigDecimal trenutnoStanje,LocalDate krajnjiRok){
        this.naziv=naziv;
        this.ciljIznos=ciljIznos;
        this.trenutnoStanje=trenutnoStanje;
        this.krajnjiRok=krajnjiRok;
    }

}
