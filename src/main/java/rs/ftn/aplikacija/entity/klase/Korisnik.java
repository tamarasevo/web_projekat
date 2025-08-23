package rs.ftn.aplikacija.entity.klase;

import java.time.LocalDate;

public class Korisnik {
    private String ime;
    private String prezime;
    private String korisnickoIme; // jedinstveno
    private String mejl;          // jedinstveno
    private String lozinkaHash;   // heširana lozinka
    private LocalDate datumRodjenja;
    private String uloga;         // "Korisnik" ili "Administrator"
    private String profilnaSlika; // putanja do slike
    private Valuta valuta;        // podrazumevana valuta
    private LocalDate datumRegistracije;
    private boolean blokiran;

    public Korisnik(String ime, String prezime, String korisnickoIme, String mejl, String lozinkaHash,
                    LocalDate datumRodjenja, String uloga, String profilnaSlika, Valuta valuta,
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

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getMejl() {
        return mejl;
    }

    public String getLozinkaHash() {
        return lozinkaHash;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public String getUloga() {
        return uloga;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public LocalDate getDatumRegistracije() {
        return datumRegistracije;
    }

    public boolean isBlokiran() {
        return blokiran;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public void setLozinkaHash(String lozinkaHash) {
        this.lozinkaHash = lozinkaHash;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public void setDatumRegistracije(LocalDate datumRegistracije) {
        this.datumRegistracije = datumRegistracije;
    }

    public void setBlokiran(boolean blokiran) {
        this.blokiran = blokiran;
    }
}
