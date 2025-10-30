package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Indirizzo;
import Gruppo4BW2BE.BW2.Payloads.IndirizzoPayload;
import Gruppo4BW2BE.BW2.Services.IndirizzoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/indirizzi")
public class IndirizzoController {

    private final IndirizzoService indirizzoService;

    public IndirizzoController(IndirizzoService indirizzoService) {
        this.indirizzoService = indirizzoService;
    }

    // ✅ Crea indirizzo (POST)
    @PostMapping
    public ResponseEntity<Indirizzo> create(@RequestBody IndirizzoPayload payload) {
        Indirizzo saved = indirizzoService.saveNewIndirizzo(payload);
        return ResponseEntity.ok(saved);
    }

    // ✅ Trova indirizzo per ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Indirizzo> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(indirizzoService.findById(id));
    }

    // ✅ Aggiorna indirizzo (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Indirizzo> update(
            @PathVariable UUID id,
            @RequestBody IndirizzoPayload payload
    ) {
        return ResponseEntity.ok(indirizzoService.findByIdAndUpdate(id, payload));
    }

    // ✅ Elimina indirizzo (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        indirizzoService.findByIdAndDelete(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Lista paginata indirizzi (GET)
    @GetMapping
    public ResponseEntity<Page<Indirizzo>> getAll(Pageable pageable) {
        return ResponseEntity.ok(indirizzoService.listaPaginata(pageable));
    }
}
