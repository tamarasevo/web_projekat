package rs.ftn.aplikacija.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import rs.ftn.aplikacija.entity.klase.Goal;
import rs.ftn.aplikacija.entity.klase.Novcanik;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.repository.GoalRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class GoalService {
    private final NovcanikService novcanikService;

    //dodati cilj u neki novcanik da li vec postojeci ili
    //napravimo novi

    //dodajemo u vidu transakcije odredjenu sumu u goal klasu
    //skidamo sa jednog novcanika
    //i ubacujemo u stedni novcanik

    private GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository, NovcanikService novcanikService) {
        this.goalRepository = goalRepository;
        this.novcanikService = novcanikService;
    }

    public Goal save(Goal g) {
        return goalRepository.save(g);
    }

    public Goal getGoal(Long id) {
        return goalRepository.findById(id).orElse(null);
    }

    public Goal dodajGoal(String naziv, BigDecimal iznos, LocalDate krajnjiRok,Long id) {
        //pronadji novcanik po id
        Novcanik novcanik=novcanikService.getById(id);

        //moramo da imamo proveru da li je novcanik stedni
        if(!novcanik.isStedni()){
            throw new RuntimeException("Novcanik nije stedni, izaberite drugi!");
        }

        //provera da vidimo da li u novcanilku vec postoji cilj
        //ako postoji izbaci poruku
        Goal postojeciCilj=goalRepository.findByNovcanik(novcanik);
        if(postojeciCilj!=null){
            throw new RuntimeException("Novcanik vec ima aktivan cilj stednje!");
        }

        Goal cilj=new Goal();
        cilj.setNaziv(naziv);
        cilj.setCiljIznos(iznos);
        cilj.setKrajnjiRok(krajnjiRok);
        cilj.setNovcanik(novcanik);

        return save(cilj);
    }

    //azuriranje moze da se vrsi samo iz jednog novcanika koji
    //poseduje goal klasu
    //videti da li treba opcija da se iz drugih novcaika vrsi
    //prenos na goal klasu
    @Transactional
    public void azurirajGoal(Transakcija transakcija){
        //nadji novcanik kome pripada transakcija
        Novcanik novcanik=transakcija.getNovcanik();

        if(novcanik.getTrenutnoStanje().compareTo(transakcija.getIznos())<0){
            throw new RuntimeException("Nema dovoljno sredstava na racunu!");
        }

        Goal cilj=goalRepository.findByNovcanik(novcanik);
        if(cilj==null){
            throw new RuntimeException("Nema aktivnih ciljeva!");
        }

        //dodamo vrendost transakcije
        novcanik.setTrenutnoStanje(novcanik.getTrenutnoStanje().subtract(transakcija.getIznos()));

        BigDecimal novoStanje=cilj.getTrenutnoStanje().add(transakcija.getIznos());
        //treba nam ako nam ono sto zelimo da uplatimo premasuje cilj
        //i ako hocemo da nam ostatak vrati u novcanik
        BigDecimal ostatak=BigDecimal.ZERO;

        if(novoStanje.compareTo(cilj.getCiljIznos())>0){
            ostatak=novoStanje.subtract(cilj.getCiljIznos());
            novoStanje=cilj.getCiljIznos();
        }

        cilj.setTrenutnoStanje(novoStanje);
        goalRepository.save(cilj);

        if(ostatak.compareTo(BigDecimal.ZERO)>0){
            novcanik.setTrenutnoStanje(novcanik.getTrenutnoStanje().add(ostatak));
        }
        novcanikService.save(novcanik);
    }

}
