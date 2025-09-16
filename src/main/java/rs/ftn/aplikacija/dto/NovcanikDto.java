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
    private String naziv;
    private BigDecimal pocetnoStanje;
    private String valuta;
    private boolean stedni;
   // private Long korisnikID;

    //setter ne radi pa pravimo nas
    public void setStedni(boolean stedni){
        this.stedni = stedni;
    }

    //getter jer ne radi pa pravimo nas
    public boolean isStedni(){
        return this.stedni;
    }
}


