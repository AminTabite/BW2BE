package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Cliente;
import Gruppo4BW2BE.BW2.Entities.Fattura;
import Gruppo4BW2BE.BW2.Exceptions.BadRequestException;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Exceptions.ValidationException;
import Gruppo4BW2BE.BW2.Repositories.ClienteRepository;
import Gruppo4BW2BE.BW2.Repositories.FatturaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

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
    public Fattura findById(Long id) {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata con id: " + id));
    }

    //modifica
    public Fattura update(Long id, Fattura nuovaFattura) {
        Fattura esistente = findById(id);

        esistente.setNumero(nuovaFattura.getNumero());
        esistente.setData(nuovaFattura.getData());
        esistente.setImporto(nuovaFattura.getImporto());
        esistente.setStato(nuovaFattura.getStato());
        esistente.setCliente(nuovaFattura.getCliente());

        return fatturaRepository.save(esistente);
    }

    //elimina
    public void delete(Long id) {
        if (!fatturaRepository.existsById(id)) {
            throw new NotFoundException("Fattura non trovata con id: " + id);
        }
        fatturaRepository.deleteById(id);
    }

    public Page<Fattura> listaPaginata(Pageable pageable) {
        return fatturaRepository.findAll(pageable);
    }

    public Page<Fattura> search(Long clienteId, String stato, LocalDate data,
                                       Integer anno, BigDecimal importoMin, BigDecimal importoMax, Pageable pageable) {

        return fatturaRepository.filtraFatture(clienteId, stato, data, anno, importoMin, importoMax, pageable);
    }
}

