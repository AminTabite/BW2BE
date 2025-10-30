package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import Gruppo4BW2BE.BW2.Services.StatoFatturaService;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stati-fattura")
public class StatoFatturaController {

    @Autowired
    private StatoFatturaService statoFatturaService;

    // ✅ Recuperare tutti gli stati fattura con paginazione
    @GetMapping
    public ResponseEntity<Page<StatoFattura>> getAllStatiFattura(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "statoFattura") String sortBy
    ) {
        Page<StatoFattura> result = statoFatturaService.getAllStatiFattura(page, size, sortBy);
        return ResponseEntity.ok(result);
    }

    // ✅ Creare un nuovo stato fattura
    @PostMapping
    public ResponseEntity<StatoFattura> createStatoFattura(@RequestParam String nome) {
        StatoFattura created = statoFatturaService.createStatoFattura(nome);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ✅ Recuperare uno stato fattura per ID
    @GetMapping("/{id}")
    public ResponseEntity<StatoFattura> getStatoFatturaById(@PathVariable UUID id) {
        StatoFattura stato = statoFatturaService.findById(id);
        return ResponseEntity.ok(stato);
    }
//
//    // ✅ Aggiornare uno stato fattura per ID
//    @PutMapping("/{id}")
//    public ResponseEntity<StatoFattura> updateStatoFattura(
//            @PathVariable UUID id,
//            @RequestParam String nuovoNome
//    ) {
//        StatoFattura updated = statoFatturaService.findByIdAndUpdate(id,);
//        return ResponseEntity.ok(updated);
//    }

    // ✅ Eliminare uno stato fattura per ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatoFattura(@PathVariable UUID id) {
        statoFatturaService.findByIdAndDelete(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Recuperare uno stato fattura per nome
    @GetMapping("/search")
    public ResponseEntity<StatoFattura> getStatoFatturaByNome(@RequestParam String nome) {
        StatoFattura stato = statoFatturaService.findByNome(nome);
        return ResponseEntity.ok(stato);
    }

    // ✅ Gestione semplice delle eccezioni
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
