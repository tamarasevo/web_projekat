package rs.ftn.aplikacija.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.dto.GoalDto;
import rs.ftn.aplikacija.dto.TransakcijaDto;
import rs.ftn.aplikacija.entity.klase.Goal;
import rs.ftn.aplikacija.entity.klase.Transakcija;
import rs.ftn.aplikacija.service.GoalService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/api/ciljstednje")
public class GoalController {

    private final GoalService goalService;
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/dodaj")
    public ResponseEntity<?> dodajGoal(@RequestBody GoalDto goalDto) {
        Goal cilj=goalService.dodajGoal(goalDto.getNaziv(),goalDto.getIznos(),goalDto.getKrajnjiCilj(),goalDto.getNovcanikId());

        if(cilj==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cilj);
    }

    @PutMapping("{id1}/{id2}/azuriraj")
    public ResponseEntity<String> azurirajGoal(@RequestBody TransakcijaDto transakcija, @PathVariable Long id1,@PathVariable Long id2) {
       try {
           goalService.azurirajGoal(id1,id2,transakcija);
           return ResponseEntity.ok("Azuriranje goal klase je uspelo!");
       }catch (RuntimeException ex){
           return ResponseEntity.badRequest().body(ex.getMessage());
       }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Goal> getGoal(@PathVariable Long id){
        Goal g=goalService.getGoal(id);
        return ResponseEntity.ok(g);
    }


}
