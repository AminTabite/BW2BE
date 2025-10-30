package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Repositories.StatoFatturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class StatoFatturaService {

    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    // ✅ Recuperare tutti gli stati paginati
    public Page<StatoFattura> getAllStatiFattura(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return statoFatturaRepository.findAll(pageable);
    }

    // ✅ Creazione nuovo stato fattura
    public StatoFattura createStatoFattura(String nome) {
        if (statoFatturaRepository.findByStatoFattura(nome).isPresent()) {
            throw new RuntimeException("Stato fattura già esistente: " + nome);
        }

        StatoFattura newStato = new StatoFattura(null, nome);
        StatoFattura saved = statoFatturaRepository.save(newStato);

        log.info("Stato fattura '{}' creato correttamente!", nome);
        return saved;
    }

    // ✅ find by ID
    public StatoFattura findById(UUID id) {
        return statoFatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato", id));
    }

//    // ✅ findById and Update
//    public StatoFattura findByIdAndUpdate(UUID id) {
//        StatoFattura found = statoFatturaRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato", id));
//
//        found.setStatoFattura(nuovoNome);
//        return statoFatturaRepository.save(found);
//    }

    // ✅ findById and Delete
    public void findByIdAndDelete(UUID id) {
        StatoFattura found = statoFatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato", id));

        statoFatturaRepository.delete(found);
        log.info("Stato fattura con ID {} eliminato correttamente.", id);
    }

    // ✅ find by nome
    public StatoFattura findByNome(String nome) {
        return statoFatturaRepository.findByStatoFattura(nome)
                .orElseThrow(() -> new RuntimeException("Stato fattura non trovato: " + nome));
    }
}
