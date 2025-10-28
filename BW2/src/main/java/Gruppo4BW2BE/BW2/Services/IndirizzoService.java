package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Indirizzo;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class IndirizzoService {

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    //crea un nuovo indirizzo
    public Indirizzo create(Indirizzo indirizzo) {
        return indirizzoRepository.save(indirizzo);
    }

    //trova indirizzo per ID
    public Indirizzo findByID(UUID id) {
        return indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Indirizzo non trovato con id: " + id));
    }

    //aggiorna un indirizzo
    public Indirizzo update(UUID id, Indirizzo indirizzoAggiornato) {
        Indirizzo indirizzoEsistente = findByID(id);

        indirizzoEsistente.setVia(indirizzoAggiornato.getVia());
        indirizzoEsistente.setCivico(indirizzoAggiornato.getCivico());
        indirizzoEsistente.setLocalita(indirizzoAggiornato.getLocalita());
        indirizzoEsistente.setCap(indirizzoAggiornato.getCap());
        indirizzoEsistente.setComune(indirizzoAggiornato.getComune());

        return indirizzoRepository.save(indirizzoEsistente);
    }

    //elimina un indirizzo
    public void delete(UUID id) {
        if (!indirizzoRepository.existsById(id)) {
            throw new NotFoundException("Indirizzo non trovato con id: " + id);
        }
        indirizzoRepository.deleteById(id);
    }

    //restituisce tutti gli indirizzi (paginati)
    public Page<Indirizzo> listaPaginata(Pageable pageable) {
        return indirizzoRepository.findAll(pageable);
    }
}
