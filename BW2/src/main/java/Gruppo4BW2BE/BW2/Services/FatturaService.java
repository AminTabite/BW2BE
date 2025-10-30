package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Cliente;
import Gruppo4BW2BE.BW2.Entities.Fattura;
import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Payloads.FatturaPayload;
import Gruppo4BW2BE.BW2.Repositories.ClienteRepository;
import Gruppo4BW2BE.BW2.Repositories.FatturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // ✅ Crea nuova fattura
    public Fattura saveNewFattura(FatturaPayload payload, UUID clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato", clienteId));

        Fattura newFattura = new Fattura(
                payload.numero(),
                payload.data(),
                payload.importo(),
                payload.stato(),
                cliente
        );

        Fattura saved = fatturaRepository.save(newFattura);
        log.info("Fattura n. {} salvata correttamente", saved.getNumero());
        return saved;
    }

    // ✅ Trova fattura per ID
    public Fattura findById(UUID id){
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata", id));
    }

    // ✅ Update fattura
    public Fattura findByIdAndUpdate(UUID id, FatturaPayload payload, UUID clienteId){

        Fattura found = findById(id);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato", clienteId));

        found.setNumero(payload.numero());
        found.setData(payload.data());
        found.setImporto(payload.importo());
        found.setStato(payload.stato());
        found.setCliente(cliente);

        return fatturaRepository.save(found);
    }

    // ✅ Elimina fattura
    public void findByIdAndDelete(UUID id){

        Fattura found = findById(id);
        fatturaRepository.delete(found);

        log.info("Fattura {} eliminata correttamente.", id);
    }

    // ✅ Lista paginata
    public Page<Fattura> getAllFatture(int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return fatturaRepository.findAll(pageable);
    }

    // ✅ Cambia stato fattura
    public Fattura cambiaStato(UUID id, StatoFattura nuovoStato) {
        Fattura fattura = findById(id);
        fattura.setStato(nuovoStato);
        return fatturaRepository.save(fattura);
    }

    // ✅ Filtri avanzati
    public Page<Fattura> search(Long clienteId, String stato, LocalDate data,
                                Integer anno, BigDecimal importoMin, BigDecimal importoMax, Pageable pageable) {

        return fatturaRepository.filtraFatture(clienteId, stato, data, anno, importoMin, importoMax, pageable);
    }
}
