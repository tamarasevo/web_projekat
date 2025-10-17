package rs.ftn.aplikacija.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    String korisnickoIme;
    String lozinka;

    public String getUsername() { return korisnickoIme; }
    public void setUsername(String username) { this.korisnickoIme = username; }

    public String getPassword() { return lozinka; }
    public void setPassword(String password) { this.lozinka = password; }
}
