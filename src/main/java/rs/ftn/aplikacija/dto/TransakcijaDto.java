package rs.ftn.aplikacija.dto;

import lombok.*;
import rs.ftn.aplikacija.entity.klase.Kategorija;
import rs.ftn.aplikacija.entity.klase.Transakcija;
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
    private BigDecimal iznos;
    private Tip tip;
    private Long kategorijaId;
    private LocalDate datum; //prihvata datum unosa transakcije
    private boolean ponavljajuca;

    public TransakcijaDto(Transakcija t) {
        //this.novcanikId = t.getNovcanik() != null ? t.getNovcanik().getId() : null;
        this.naziv = t.getNaziv();
        this.iznos = t.getIznos();
        this.tip = t.getTip();
        //this.datum = t.getDatumTransakcije();
        this.ponavljajuca = t.isPonavljajuca();
    }
}
