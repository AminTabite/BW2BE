package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Indirizzo;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Payloads.IndirizzoPayload;
import Gruppo4BW2BE.BW2.Repositories.IndirizzoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class IndirizzoService {

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    // ✅ Crea nuovo indirizzo (stile UtenteService -> saveNewUtente)
    public Indirizzo saveNewIndirizzo(IndirizzoPayload payload) {
        Indirizzo newIndirizzo = new Indirizzo(
                payload.via(),
                payload.civico(),
                payload.localita(),
                payload.cap(),
                payload.comune()
        );

        Indirizzo saved = indirizzoRepository.save(newIndirizzo);
        log.info("Indirizzo salvato correttamente! ID: " + saved.getId());
        return saved;
    }

    // ✅ findByID (stile findById)
    public Indirizzo findById(UUID id) {
        return indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Indirizzo non trovato", id));
    }

    // ✅ update (stile findByIdAndUpdate)
    public Indirizzo findByIdAndUpdate(UUID id, IndirizzoPayload payload) {
        Indirizzo found = this.findById(id);

        found.setVia(payload.via());
        found.setCivico(payload.civico());
        found.setLocalita(payload.localita());
        found.setCap(payload.cap());
        found.setComune(payload.comune());

        Indirizzo updated = indirizzoRepository.save(found);
        log.info("Indirizzo con ID " + id + " aggiornato correttamente.");
        return updated;
    }

    // ✅ delete (stile findByIdAndDelete)
    public void findByIdAndDelete(UUID id) {
        Indirizzo found = this.findById(id);
        indirizzoRepository.delete(found);
        log.info("Indirizzo con ID " + id + " eliminato correttamente.");
    }

    // ✅ lista paginata (stile getAllClienti)
    public Page<Indirizzo> listaPaginata(Pageable pageable) {
        return indirizzoRepository.findAll(pageable);
    }
}
