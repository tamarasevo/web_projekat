package rs.ftn.aplikacija.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ftn.aplikacija.entity.klase.Valuta;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class FrankfurtApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://api.frankfurter.app/latest?from={from}&to=RSD";

    /**
     * Dohvata podatke o valuti sa Frankfurter API-ja.
     * @param nazivValute kod valute npr. "EUR", "USD"
     * @return Valuta objekat sa kodom i kursom
     */
    public Valuta getValuta(String nazivValute) {

            if ("RSD".equalsIgnoreCase(nazivValute)) {
                Valuta valuta = new Valuta();
                valuta.setNaziv("RSD");
                valuta.setVrednost(BigDecimal.ONE); // kurs 1:1
                return valuta;
            }else if ("EUR".equalsIgnoreCase(nazivValute)) {
                Valuta valuta = new Valuta();
                valuta.setNaziv("EUR");
                valuta.setVrednost(new BigDecimal("117.25"));
                return valuta;
            }

                Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class, nazivValute);

            if (response == null || !response.containsKey("rates")) {
                throw new IllegalArgumentException("Nepoznata valuta: " + nazivValute);
            }

            // rates je mapa "RSD": 117.25 npr.
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            BigDecimal kurs = BigDecimal.valueOf(rates.get("RSD"));

            Valuta valuta = new Valuta();
            valuta.setNaziv(nazivValute);
            valuta.setNaziv(nazivValute); // možeš mapirati na stvarno ime valute
            valuta.setVrednost(kurs);

            return valuta;


    }
}


