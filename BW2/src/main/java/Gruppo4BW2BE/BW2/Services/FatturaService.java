package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Cliente;
import Gruppo4BW2BE.BW2.Entities.Fattura;
import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Repositories.ClienteRepository;
import Gruppo4BW2BE.BW2.Repositories.FatturaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    //crea nuova fattura
    public Fattura create(Fattura fattura, UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con id: " + clienteId));
        fattura.setCliente(cliente);
        return fatturaRepository.save(fattura);
    }

    //trova per ID
    public Fattura findById(UUID id) {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FatturaPayload non trovata con id: " + id));
    }

    //modifica
    public Fattura update(UUID id, Fattura nuovaFattura) {
        Fattura esistente = findById(id);

        esistente.setNumero(nuovaFattura.getNumero());
        esistente.setData(nuovaFattura.getData());
        esistente.setImporto(nuovaFattura.getImporto());
        esistente.setStato(nuovaFattura.getStato());
        esistente.setCliente(nuovaFattura.getCliente());

        return fatturaRepository.save(esistente);
    }

    //elimina
    public void delete(UUID id) {
        if (!fatturaRepository.existsById(id)) {
            throw new NotFoundException("FatturaPayload non trovata con id: " + id);
        }
        fatturaRepository.deleteById(id);
    }

    public Page<Fattura> listaPaginata(Pageable pageable) {
        return fatturaRepository.findAll(pageable);
    }

    public Fattura cambiaStato(UUID id, StatoFattura nuovoStato) {
        Fattura fattura = fatturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FatturaPayload non trovata"));
        fattura.setStato(nuovoStato);
        return fatturaRepository.save(fattura);
    }

    public Page<Fattura> getFattureByStato(StatoFattura stato, Pageable pageable) {
        return fatturaRepository.findByStato(stato, pageable);
    }

    public Page<Fattura> search(Long clienteId, String stato, LocalDate data,
                                Integer anno, BigDecimal importoMin, BigDecimal importoMax, Pageable pageable) {

        return fatturaRepository.filtraFatture(clienteId, stato, data, anno, importoMin, importoMax, pageable);
    }
}


