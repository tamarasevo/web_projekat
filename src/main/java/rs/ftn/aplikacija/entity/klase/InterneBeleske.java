package rs.ftn.aplikacija.entity.klase;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterneBeleske {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sadrzaj ;
    private LocalDateTime datumKreiranja;


    private Long korisnikID;

}
