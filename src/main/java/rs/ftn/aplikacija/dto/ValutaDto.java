package rs.ftn.aplikacija.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValutaDto {
    private String naziv;
    private BigDecimal vrednost;
}
