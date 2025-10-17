package rs.ftn.aplikacija.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.dto.LoginRequest;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.service.KorisnikService;
import rs.ftn.aplikacija.config.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KorisnikService korisnikService;

    public AuthController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login, HttpServletResponse response) {
        Korisnik korisnik = korisnikService.findByKorisnickoIme(login.getKorisnickoIme());
        if (korisnik == null || !korisnik.getLozinkaHash().equals(login.getLozinka())) {
            return ResponseEntity.status(401).build();
        }

        String token = JwtUtil.generateToken(korisnik.getId(), korisnik.getUloga().name());

        // Dodaj token u cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // sigurnije
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);     // 1 dan
        response.addCookie(cookie);

        return ResponseEntity.ok(korisnik);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok("Izlogovan");
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(@CookieValue(value = "token", required = false) String token) {
        if (token != null && token.equals("dummy-jwt-token")) {
            return ResponseEntity.ok("ulogovan");
        } else {
            return ResponseEntity.status(401).body("nije ulogovan");
        }
    }
}


