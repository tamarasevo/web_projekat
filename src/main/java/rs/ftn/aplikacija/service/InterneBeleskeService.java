package rs.ftn.aplikacija.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.InterneBeleske;
import rs.ftn.aplikacija.repository.InterneBeleskeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterneBeleskeService {
    @Autowired
    private InterneBeleskeRepository beleskaRepository;


    @Transactional
    public InterneBeleske dodajBelesku(Long korisnikID, String sadrzaj) {
        InterneBeleske beleska = new InterneBeleske();
        beleska.setKorisnikID(korisnikID);
        beleska.setSadrzaj(sadrzaj);
        beleska.setDatumKreiranja(LocalDateTime.now());
        return beleskaRepository.save(beleska);
    }



    public List<InterneBeleske> getBeleskeZaKorisnika(Long korisnikId) {
        return beleskaRepository.findByKorisnikId(korisnikId);
    }


}
