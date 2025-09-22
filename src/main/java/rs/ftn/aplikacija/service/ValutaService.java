package rs.ftn.aplikacija.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ftn.aplikacija.entity.klase.Valuta;
import rs.ftn.aplikacija.repository.ValutaRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ValutaService {

    private final ValutaRepository valutaRepository;
    public ValutaService(ValutaRepository valutaRepository) {
        this.valutaRepository = valutaRepository;
    }


    public BigDecimal konvertuj(String from, String to, BigDecimal amount) {

        if (from.equalsIgnoreCase(to)) {
            return amount; // nema konverzije
        }

        // uzimamo "vrednost" valute u odnosu na RSD
        Valuta iz = getValuta(from);
        Valuta u = getValuta(to);

        if (iz == null || u == null) {
            throw new RuntimeException("Nepoznata valuta");
        }

        BigDecimal iznosURSD = amount.multiply(iz.getVrednost());


        BigDecimal konvertovan = iznosURSD.divide(u.getVrednost(), 2, RoundingMode.HALF_UP);

        return konvertovan;
    }

    public Valuta getValuta(String nazivValute) {
        Valuta valuta = new Valuta();

        switch (nazivValute.toUpperCase()) {
            case "RSD":
                valuta.setNaziv("RSD");
                valuta.setVrednost(BigDecimal.ONE);
                break;
            case "EUR":
                valuta.setNaziv("EUR");
                valuta.setVrednost(new BigDecimal("117.25"));
                break;
            case "USD":
                valuta.setNaziv("USD");
                valuta.setVrednost(new BigDecimal("109.50"));
                break;
            default:
                throw new IllegalArgumentException("Nepoznata valuta: " + nazivValute);
        }

        return valuta;
    }


    //logika konverzije
    //videti kako dodati onu funckionalost sa apijem
   /* public BigDecimal konvertuj(BigDecimal iznos, String izValute, String uValutu){
        Valuta iz=valutaRepository.findByNaziv(izValute);
        Valuta u=valutaRepository.findByNaziv(uValutu);

        if(iz==null || u==null){
            throw new RuntimeException("Nepoznata valuta");
        }

        BigDecimal iznosEUR=iznos.divide(BigDecimal.valueOf(iz.getVrednost()),10, RoundingMode.HALF_UP);
        BigDecimal rezultat=iznosEUR.multiply(BigDecimal.valueOf(u.getVrednost())).setScale(2, RoundingMode.HALF_UP);

        return rezultat;
    }*/

}
