package rs.ftn.aplikacija.entity.klase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfi{

    @Bean
    public UserDetailsService userDetailsService() {
        // dummy user, Spring Security neće prijavljivati greške
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("dummy")
                .password("{noop}dummy")
                .roles("KORISNIK")
                .build());
        return manager;
    }
}

