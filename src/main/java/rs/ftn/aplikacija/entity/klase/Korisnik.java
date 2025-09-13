package rs.ftn.aplikacija.entity.klase;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ftn.aplikacija.entity.klase.enums.Uloga;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String prezime;
    private String korisnickoIme; // jedinstveno
    private String mejl;          // jedinstveno
    private String lozinkaHash;   // heširana lozinka
    private LocalDate datumRodjenja;

    @Enumerated(EnumType.STRING)
    private Uloga uloga;         // "Korisnik" ili "Administrator"

    private String profilnaSlika; // putanja do slike
    private LocalDate datumRegistracije;
    private boolean blokiran;


    //da li nam je uopste potrebna valuta ako se ona
    //vec nalazi u novcaniku
    //nije potrebna ali uz ovu antonaciju nema konflikta
    @ManyToOne
    private Valuta valuta;        // podrazumevana valuta


    //Kornisnik moze da ima vise novcanika i pravimo set novcanika
    @OneToMany(mappedBy="korisnik")
    @JsonManagedReference
    private Set<Novcanik> novcanici= new HashSet<>();

    @OneToMany(mappedBy="korisnik")
    private Set<Transakcija> transakcije=new HashSet<>();

    @OneToMany(mappedBy="korisnik")
    private Set<Kategorija> kategorija=new HashSet<>();

    @OneToMany(mappedBy="korisnik")
    private Set<Goal> stednja=new HashSet<>();




    public Korisnik(String ime, String prezime, String korisnickoIme, String mejl, String lozinkaHash,
                    LocalDate datumRodjenja, Uloga uloga, String profilnaSlika, Valuta valuta,
                    LocalDate datumRegistracije, boolean blokiran) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mejl = mejl;
        this.lozinkaHash = lozinkaHash;
        this.datumRodjenja = datumRodjenja;
        this.uloga = uloga;
        this.profilnaSlika = profilnaSlika;
        this.valuta = valuta;
        this.datumRegistracije = datumRegistracije;
        this.blokiran = blokiran;
    }


}
