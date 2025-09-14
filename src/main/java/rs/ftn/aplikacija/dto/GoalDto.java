package rs.ftn.aplikacija.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

//klasa koja nam pomaze da komuniciramo sa frontendom
//filtira nam podatke
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto{
    private String naziv;
    private BigDecimal iznos;
    private LocalDate krajnjiCilj;
    private Long novcanikId;

}
