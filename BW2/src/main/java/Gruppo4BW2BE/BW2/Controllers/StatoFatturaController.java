package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import Gruppo4BW2BE.BW2.Services.StatoFatturaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/statifattura")
public class StatoFatturaController {

    private final StatoFatturaService service;

    public StatoFatturaController(StatoFatturaService service) {
        this.service = service;
    }

    //crea uno stato fattura
    @PostMapping
    public ResponseEntity<StatoFattura> create(@RequestBody StatoFattura s) {
        StatoFattura created = service.create(s);
        return ResponseEntity.ok(created);
    }

    //recupera stato per id
    @GetMapping("/{id}")
    public ResponseEntity<StatoFattura> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    //recupera stato per code (es. /api/statifattura/code/EMESSA)
    @GetMapping("/code/{code}")
    public ResponseEntity<StatoFattura> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    //aggiorna stato
    @PutMapping("/{id}")
    public ResponseEntity<StatoFattura> update(@PathVariable UUID id, @RequestBody StatoFattura s) {
        return ResponseEntity.ok(service.update(id, s));
    }

    //elimina stato
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //lista paginata
    @GetMapping
    public ResponseEntity<Page<StatoFattura>> list(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }
}
