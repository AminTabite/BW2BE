package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Repositories.StatoFatturaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class StatoFatturaService {

    private final StatoFatturaRepository repo;

    public StatoFatturaService(StatoFatturaRepository repo) {
        this.repo = repo;
    }

    public StatoFattura create(StatoFattura payload) {
        if (payload == null) throw new IllegalArgumentException("Payload vuoto");
        if (payload.getCode() == null || payload.getCode().isBlank())
            throw new IllegalArgumentException("Code obbligatorio");
        if (repo.existsByCode(payload.getCode()))
            throw new IllegalArgumentException("Code già presente: " + payload.getCode());
        return repo.save(payload);
    }

    public StatoFattura findById(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("StatoFattura non trovato con id: " + id.toString()));
    }

    public StatoFattura findByCode(String code) {
        return repo.findByCode(code)
                .orElseThrow(() -> new NotFoundException("StatoFattura non trovato con code: " + code));
    }

    public StatoFattura update(UUID id, StatoFattura payload) {
        StatoFattura existing = findById(id);
        if (payload.getCode() != null && !payload.getCode().isBlank()) {
            //se cambia il code e il nuovo code è già presente -> error
            if (!existing.getCode().equals(payload.getCode()) && repo.existsByCode(payload.getCode())) {
                throw new IllegalArgumentException("Code già presente: " + payload.getCode());
            }
            existing.setCode(payload.getCode());
        }
        if (payload.getDescription() != null) existing.setDescription(payload.getDescription());
        return repo.save(existing);
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new NotFoundException("StatoFattura non trovato con id: " + id.toString());
        repo.deleteById(id);
    }

    public Page<StatoFattura> list(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
