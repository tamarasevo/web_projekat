package rs.ftn.aplikacija.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.entity.klase.enums.Tip;
import rs.ftn.aplikacija.entity.klase.enums.Ucestalost;
import rs.ftn.aplikacija.repository.TransakcijaRepository;
import rs.ftn.aplikacija.entity.klase.Transfer;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransakcijaService {

    private final TransakcijaRepository transakcijaRepository;
    @Autowired
    private NovcanikService novcanikService;
    @Autowired
    private ValutaService valutaService;

    public TransakcijaService(TransakcijaRepository transakcijaRepository) {
        this.transakcijaRepository = transakcijaRepository;
    }


    public Transakcija save(Transakcija transakcija){
        return transakcijaRepository.save(transakcija);
    }

    public Transakcija update(Transakcija transakcija){
        return transakcijaRepository.save(transakcija);
    }

    //trebamo da trazimo po listi novcanika
    //pregled svih transakcija
    public List<Transakcija> getTransakcije(Long novcanikId){
        return transakcijaRepository.findByNovcanikId(novcanikId);
    }

    //pregled jedne transakcije
    /*public List<Transakcija> getTransakciju(Long novcanikId){
        return transakcijaRepository.findById(novcanikId);
    }*/
    public void deleteTransakcija(Transakcija transakcija){
        transakcijaRepository.delete(transakcija);
    }

    //definisanje ponavljajuce transakcije
    //stavimo i na kom nivou ucestalosti (NEDELJNA,MESECNA,GODISNJA)
    public Boolean ponavljajuca(Long transakcijaId, Ucestalost ucestalost){
        Transakcija transakcija=transakcijaRepository.findById(transakcijaId).orElse(null);
        if(transakcija==null)
            return false;

        transakcija.setPonavljajuca(true);
        transakcija.setUcestalost(ucestalost);
        //potrebno je uvek cuvati kako bi se zabelezilo u bazi
        transakcijaRepository.save(transakcija);
        return true;
    }

    //iskljucivanje ponavljajuce transakicje
    public Boolean iskljuciPonavljajuca(Long transakcijaId){
        Transakcija transakcija=transakcijaRepository.findById(transakcijaId).orElse(null);
        if(transakcija==null)
            return false;

        transakcija.setPonavljajuca(false);
        transakcijaRepository.save(transakcija);
        return true;
    }

    //Automatsko generisanje transakcije u zadatom intervalu
    @Scheduled(cron="0 0 0 * * *")
    public void generisiTransakcija(){
        //prodji kroz listu tranbsakcija,ali samo pojnavljajucih
        List<Transakcija> transakcije = transakcijaRepository.findByPonavljajucaTrue();

        for(Transakcija t: transakcije){
            LocalDate danas= LocalDate.now();

            //ako imamo neke
            boolean trebaGenerisati = false;

            switch(t.getUcestalost()){
                case NEDELJNA:
                    trebaGenerisati = t.getDatumTransakcije().plusWeeks(1).isBefore(danas) || t.getDatumTransakcije().plusWeeks(1).isEqual(danas);
                    break;
                case MESECNA:
                    trebaGenerisati=t.getDatumTransakcije().plusMonths(1).isBefore(danas) || t.getDatumTransakcije().plusMonths(1).isEqual(danas);
                    break;
                case GODISNJA:
                    trebaGenerisati=t.getDatumTransakcije().plusYears(1).isEqual(danas) || t.getDatumTransakcije().plusYears(1).isEqual(danas);
                    break;
            }

            if(trebaGenerisati){
             Transakcija nova=new Transakcija();
             nova.setDatumTransakcije(danas);
             transakcijaRepository.save(nova);

            }

        }
    }
    //obezbedjujemo da se izvrsava u okviru jedne transakcije
    @Transactional
    public void transferIzmedjuNovcanika(Long izNovcanika, Long uNovcanik, BigDecimal iznos){
        Novcanik iz=novcanikService.getById(izNovcanika);
        Novcanik u=novcanikService.getById(uNovcanik);

        if(iz.getTrenutnoStanje().compareTo(iznos)<0){
            throw new RuntimeException("Nema dovoljno sredstava");
        }

        iz.setTrenutnoStanje(iz.getTrenutnoStanje().subtract(iznos));
        novcanikService.save(iz);

        BigDecimal iznosPrimaoca;
        if(iz.getValuta().equals(u.getValuta())){
            iznosPrimaoca=iznos;
        }else{
            iznosPrimaoca = valutaService.konvertuj(iz.getValuta().getNaziv(),u.getValuta().getNaziv(),iznos);
        }

        u.setTrenutnoStanje(u.getTrenutnoStanje().add(iznosPrimaoca));
        novcanikService.save(u);

        Transakcija t1=new Transakcija();
        t1.setNaziv("Transfer");
        t1.setNovcanik(iz);
        t1.setIznos(iznos);
        t1.setTip(Tip.TROSAK);
        transakcijaRepository.save(t1);

        Transakcija t2=new Transakcija();
        t2.setNaziv("Transfer");
        t2.setNovcanik(u);
        t2.setIznos(iznosPrimaoca);
        t2.setTip(Tip.PRIHOD);
        transakcijaRepository.save(t2);
    }

    //za pregled transakcije, po danu
    /*public List<Transakcija> findNovcanikAndDan(Long novcanikId,LocalDate datum){
        return transakcijaRepository.findByNovcanikAndDatumTransakcija(novcanikId,datum);
    }*/

   /* public List<Transakcija> findByNovcanikAndNedelja(Long novcanikId,LocalDate datum){
        LocalDate start=datum.with(DayOfWeek.MONDAY);
        LocalDate end=datum.with(DayOfWeek.SATURDAY);

    }*/


}
