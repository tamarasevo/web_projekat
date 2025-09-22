package rs.ftn.aplikacija.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KorisnikDTO {
    //obavezna polja

    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String email;
    private String lozinka;
    private LocalDate datumRodjenja;
    private String uloga;

    //opciona polja
   // private String valuta;
    private String slika;
}
