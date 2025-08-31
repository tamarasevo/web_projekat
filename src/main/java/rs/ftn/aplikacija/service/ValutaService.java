package rs.ftn.aplikacija.service;

import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.Valuta;
import rs.ftn.aplikacija.repository.ValutaRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ValutaService {

    private final ValutaRepository valutaRepository;
    public ValutaService(ValutaRepository valutaRepository) {
        this.valutaRepository = valutaRepository;
    }

    //logika konverzije
    //videti kako dodati onu funckionalost sa apijem
    public BigDecimal konvertuj(BigDecimal iznos, String izValute, String uValutu){
        Valuta iz=valutaRepository.findByNaziv(izValute);
        Valuta u=valutaRepository.findByNaziv(uValutu);

        if(iz==null || u==null){
            throw new RuntimeException("Nepoznata valuta");
        }

        BigDecimal iznosEUR=iznos.divide(BigDecimal.valueOf(iz.getVrednost()),10, RoundingMode.HALF_UP);
        BigDecimal rezultat=iznosEUR.multiply(BigDecimal.valueOf(u.getVrednost())).setScale(2, RoundingMode.HALF_UP);

        return rezultat;
    }

}
