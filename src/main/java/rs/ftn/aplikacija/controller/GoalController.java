package rs.ftn.aplikacija.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ftn.aplikacija.dto.GoalDto;
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

    @PutMapping("/azuriraj")
    public ResponseEntity<String> azurirajGoal(@RequestBody Transakcija transakcija) {
       try {
           goalService.azurirajGoal(transakcija);
           return ResponseEntity.ok("Azuriranje goal klase je uspesno!");
       }catch (RuntimeException ex){
           return ResponseEntity.badRequest().body(ex.getMessage());
       }
    }


}
