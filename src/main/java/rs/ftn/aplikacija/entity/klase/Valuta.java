package rs.ftn.aplikacija.entity.klase;

public class Valuta {
    private String naziv;  // RSD, EUR, USD
    private double vrednost; // u odnosu na EUR

    public Valuta(String naziv, double vrednost) {
        this.naziv = naziv;
        this.vrednost = vrednost;
    }

    public String getNaziv() {
        return naziv;
    }

    public double getVrednost() {
        return vrednost;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setVrednost(double vrednost) {
        this.vrednost = vrednost;
    }

    /*@Override
    public String toString() {
        return naziv + " (kurs: " + vrednost + " prema EUR)";
    }*/
}

