package rs.ftn.aplikacija.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    String korisnickoIme;
    String lozinka;
}
