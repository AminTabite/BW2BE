package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Indirizzo;
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

    public IndirizzoController(IndirizzoService indirizziService) {
        this.indirizzoService = indirizziService;
    }

    //crea un nuovo indirizzo
    @PostMapping
    public ResponseEntity<Indirizzo> create(@RequestBody Indirizzo indirizzo) {
        Indirizzo created = indirizzoService.create(indirizzo);
        return ResponseEntity.ok(created);
    }

    //ottieni indirizzo per ID
    @GetMapping("/{id}")
    public ResponseEntity<Indirizzo> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(indirizzoService.findByID(id));
    }

    //aggiorna indirizzo
    @PutMapping("/{id}")
    public ResponseEntity<Indirizzo> update(@PathVariable UUID id, @RequestBody Indirizzo indirizzo) {
        Indirizzo updated = indirizzoService.update(id, indirizzo);
        return ResponseEntity.ok(updated);
    }

    //elimina indirizzo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        indirizzoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //lista paginata di tutti gli indirizzi
    @GetMapping
    public ResponseEntity<Page<Indirizzo>> listaIndirizzi(Pageable pageable) {
        Page<Indirizzo> indirizzi = indirizzoService.listaPaginata(pageable);
        return ResponseEntity.ok(indirizzi);
    }
}
