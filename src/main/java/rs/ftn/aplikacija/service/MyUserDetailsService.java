package rs.ftn.aplikacija.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.Korisnik;
import rs.ftn.aplikacija.repository.KorisnikRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private KorisnikRepository korisnikRepository; // tvoja repozitorij klasa

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Traži korisnika u bazi
        Korisnik korisnik = korisnikRepository.findByKorisnickoIme(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Vrati UserDetails koji Spring Security koristi za autentifikaciju
        return User.builder()
                .username(korisnik.getKorisnickoIme())
                .password(korisnik.getLozinkaHash()) // mora biti enkodirana lozinka
                .roles(korisnik.getUloga().name())        // npr. ROLE_USER
                .build();
    }
}

