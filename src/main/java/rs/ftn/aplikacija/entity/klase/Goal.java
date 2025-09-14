package rs.ftn.aplikacija.entity.klase;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private BigDecimal ciljIznos;
    private BigDecimal trenutnoStanje;
    private LocalDate krajnjiRok;

    //ipak jedan cilj po novcaniku
   // @ManyToOne
    @OneToOne
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
