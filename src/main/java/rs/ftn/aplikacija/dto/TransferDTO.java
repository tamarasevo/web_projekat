package rs.ftn.aplikacija.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
  // @JsonProperty("izNovcanik")
    private Long izNovcanika;
  // @JsonProperty("uNovcanik")
    private Long asd;
    private BigDecimal iznos;

   /* public Long getIzNovcanika() { return izNovcanika; }
    public void setIzNovcanika(Long izNovcanika) { this.izNovcanika = izNovcanika; }

    public Long getUNovcanik() { return uNovcanik; }
    public void setUNovcanik(Long uNovcanik) { this.uNovcanik = uNovcanik; }

    public BigDecimal getIznos() { return iznos; }
    public void setIznos(BigDecimal iznos) { this.iznos = iznos; }*/
}
