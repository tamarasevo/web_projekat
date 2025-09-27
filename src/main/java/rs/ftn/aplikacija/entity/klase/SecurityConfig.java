package rs.ftn.aplikacija.entity.klase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())      // isključujemo CSRF za REST
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()      // svi endpoint-i slobodni
                )
                .formLogin(form -> form.disable()) // onemogućavamo form login
                .httpBasic(basic-> basic.disable()); // onemogućavamo basic auth

        return http.build();
    }
}