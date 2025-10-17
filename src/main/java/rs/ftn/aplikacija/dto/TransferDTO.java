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
    private Long asd;//u koji novcanik
    private BigDecimal iznos;

}
