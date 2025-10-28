package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Ruolo;
import Gruppo4BW2BE.BW2.Repositories.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RuoloService {
    @Autowired
    private RuoloRepository ruoloRepository;

    public List<Ruolo> getAllRuoli() {

        return ruoloRepository.findAll();
    }


    public Ruolo findByNome(String nome) {
        return ruoloRepository.findByNome(nome)

                .orElseThrow(() -> new RuntimeException("Ruolo non trovato: " + nome));

    }

    public Ruolo createRuolo(String nome) {
        if (ruoloRepository.findByNome(nome).isPresent()) {
            throw new RuntimeException("Ruolo già esistente: " + nome);
        }
        return ruoloRepository.save(new Ruolo(null, nome));
    }

    public void deleteRuolo(UUID id) {
        ruoloRepository.deleteById(id);
    }



}
