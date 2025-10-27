package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Fattura;
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

    //creazione fattura
    @PostMapping("/cliente/{clienteId}")
    //@PathVariable prende l'id del cliente dalla URL
    //@RequestBody prende l’oggetto Fattura
    public ResponseEntity<Fattura> create(@PathVariable UUID clienteId, @RequestBody Fattura fattura) {
        //chiama il service per creare la fattura associata al cliente
        Fattura created = service.create(fattura, clienteId);
        return ResponseEntity.ok(created);
    }

    //aggiornamento
    @PutMapping("/{id}")
    public ResponseEntity<Fattura> update(@PathVariable Long id, @RequestBody Fattura fattura) {
        //chiama il service per aggiornare la fattura esistente
        Fattura updated = service.update(id, fattura);
        return ResponseEntity.ok(updated);
    }

    //elimina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        //chiama il service per eliminare la fattura
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //ricerca per Id
    @GetMapping("/{id}")
    public ResponseEntity<Fattura> findById(@PathVariable Long id) {
        //chiama il service per recuperare la fattura tramite ID
        return ResponseEntity.ok(service.findById(id));
    }

    //ricerca
    @GetMapping
    public ResponseEntity<Page<Fattura>> search(
            @RequestParam(required = false) Long clienteId,     //filtra per id cliente
            @RequestParam(required = false) String stato,       //filtra per stato fattura
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,   //filtra per data esatta
            @RequestParam(required = false) Integer anno,            //filtra per anno
            @RequestParam(required = false) BigDecimal minImporto,   //filtra importi >= min
            @RequestParam(required = false) BigDecimal maxImporto,   //filtra importi <= max
            Pageable pageable
    ) {
        //chiama il service per applicare tutti i filtri e ottenere la pagina di risultati
        Page<Fattura> page = service.search(clienteId, stato, data, anno, minImporto, maxImporto, pageable);
        return ResponseEntity.ok(page);
    }
}

