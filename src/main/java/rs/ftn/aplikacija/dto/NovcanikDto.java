package rs.ftn.aplikacija.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NovcanikDto {
    private Long novcanikId;
    private String naziv;
    private BigDecimal pocetnoStanje;
    private String valuta;
    private boolean stedni;

}


