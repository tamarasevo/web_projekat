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

    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal konvertuj(String from, String to, BigDecimal amount) {
        String url = String.format(
                "https://api.frankfurter.dev/v1/latest?base=%s&symbols=%s",
                from, to
        );

        // API vraća JSON u obliku { "rates": { "USD": 1.07 } ... }
        Map<String, Object> response =restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("rates")) {
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            Double rate = rates.get(to);

            if (rate != null) {
                return amount.multiply(BigDecimal.valueOf(rate));
            }
        }

        throw new RuntimeException("Konverzija nije uspela!");
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
