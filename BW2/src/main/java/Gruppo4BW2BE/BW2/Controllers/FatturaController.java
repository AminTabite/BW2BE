package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Fattura;
import Gruppo4BW2BE.BW2.Services.FatturaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    private final FatturaService service;

    public FatturaController(FatturaService service) {
        this.service = service;
    }

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Fattura> create(@PathVariable UUID clienteId, @RequestBody Fattura fattura) {
        Fattura created = service.create(fattura, clienteId);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fattura> aggiornaFattura(@PathVariable Long id, @RequestBody Fattura fattura) {
        Fattura updated = service.update(id, fattura);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaFattura(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fattura> trovaPerId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Fattura>> ricercaFatture(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String stato,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) BigDecimal minImporto,
            @RequestParam(required = false) BigDecimal maxImporto,
            Pageable pageable
    ) {
        Page<Fattura> page = service.search(clienteId, stato, data, anno, minImporto, maxImporto, pageable);
        return ResponseEntity.ok(page);
    }
}

