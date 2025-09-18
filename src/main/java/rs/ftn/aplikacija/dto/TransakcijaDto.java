package rs.ftn.aplikacija.dto;

import lombok.*;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.entity.klase.enums.Tip;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransakcijaDto {
    private Long novcanikId;
    private String naziv;
    //private String valuta;
    private BigDecimal iznos;
    private Tip tip;
    //private Kategorija kategorija;
    //private LocalDate datum; //prihvata datum unosa transakcije
    ;
}
