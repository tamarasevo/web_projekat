package rs.ftn.aplikacija.entity.klase;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//Samo pomocna klasa za transfer
@Getter
@Setter
public class Transfer {
    private Long fromNovcanik; //id novcanika sa kog skidamo
    private Long toNovcanik; //id novcanika kome ide priliv
    private BigDecimal iznos;

    public Transfer(Long fromNovcanik, Long toNovcanik, BigDecimal iznos) {
        this.fromNovcanik=fromNovcanik;
        this.toNovcanik=toNovcanik;
        this.iznos=iznos;
    }
}
