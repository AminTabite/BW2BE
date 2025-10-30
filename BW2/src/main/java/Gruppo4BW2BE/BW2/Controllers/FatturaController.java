package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Fattura;
import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import Gruppo4BW2BE.BW2.Payloads.FatturaPayload;
import Gruppo4BW2BE.BW2.Services.FatturaService;
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

    // ✅ Creazione fattura con Cliente associato
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Fattura> create(
            @PathVariable UUID clienteId,
            @RequestBody FatturaPayload payload
    ) {
        Fattura created = service.saveNewFattura(payload, clienteId);
        return ResponseEntity.ok(created);
    }

    // ✅ Aggiornamento
    @PutMapping("/{id}")
    public ResponseEntity<Fattura> update(
            @PathVariable UUID id,
            @RequestBody FatturaPayload payload,
            @PathVariable UUID clienteid
    ) {
        Fattura updated = service.findByIdAndUpdate(id, payload,clienteid );
        return ResponseEntity.ok(updated);
    }

    // ✅ Elimina fattura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.findByIdAndDelete(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Cerca fattura per ID
    @GetMapping("/{id}")
    public ResponseEntity<Fattura> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // ✅ Cambia stato fattura
    @PatchMapping("/{id}/stato")
    public ResponseEntity<Fattura> cambiaStato(
            @PathVariable UUID id,
            @RequestParam StatoFattura nuovoStato
    ) {
        return ResponseEntity.ok(service.cambiaStato(id, nuovoStato));
    }

//    // ✅ Fatture per stato
//    @GetMapping("/stato/{stato}")
//    public ResponseEntity<Page<Fattura>> getFattureByStato(
//            @PathVariable StatoFattura stato,
//            Pageable pageable
//    ) {
//        return ResponseEntity.ok(service.(stato, pageable));
//    }
//
//    // ✅ Filtri fatture
//    @GetMapping
//    public ResponseEntity<Page<Fattura>> search(
//            @RequestParam(required = false) UUID clienteId,
//            @RequestParam(required = false) String stato,
//            @RequestParam(required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
//            @RequestParam(required = false) Integer anno,
//            @RequestParam(required = false) BigDecimal minImporto,
//            @RequestParam(required = false) BigDecimal maxImporto,
//            Pageable pageable
//    ) {
//        return ResponseEntity.ok(
//                service.search(clienteId, stato, data, anno, minImporto, maxImporto, pageable)
//        );
//    }
}
