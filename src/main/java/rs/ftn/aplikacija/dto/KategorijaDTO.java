package rs.ftn.aplikacija.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ftn.aplikacija.entity.klase.enums.Tip;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KategorijaDTO {
    private String naziv;
    private Tip tip;
    private boolean aktivna=true;

}
