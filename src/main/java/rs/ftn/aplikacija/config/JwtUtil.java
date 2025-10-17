package rs.ftn.aplikacija.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "tajni_kljuc_treba_da_bude_duzi_za_HS256";
    private static final long EXPIRATION = 1000 * 60 * 60; // 1 sat
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(Long korisnikId, String uloga) {
        return Jwts.builder()
                .claim("id", korisnikId)
                .claim("uloga", uloga)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Long getKorisnikId(String token) {
        return ((Number) Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")).longValue();
    }

    public static String getUloga(String token) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("uloga");
    }
}

