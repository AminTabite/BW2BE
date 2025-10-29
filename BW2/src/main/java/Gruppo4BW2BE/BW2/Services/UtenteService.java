package Gruppo4BW2BE.BW2.Services;


import Gruppo4BW2BE.BW2.Entities.Cliente;
import Gruppo4BW2BE.BW2.Entities.Ruolo;
import Gruppo4BW2BE.BW2.Entities.Utente;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Payloads.UtentePayload;
import Gruppo4BW2BE.BW2.Repositories.RuoloRepository;
import Gruppo4BW2BE.BW2.Repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UtenteService {
    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    PasswordEncoder bcrypt;

    @Autowired
    RuoloRepository ruoloRepository;


    // Recuperare tutti gli Utenti paginati
    public Page<Utente> getAllClienti(int pageNumber, int pageSize, String sortBy) {
        // Oggetto Pageable per la paginazione
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        // Chiamiamo findAll
        return utenteRepository.findAll(pageable);
    }




    //Salvataggio Utente

    public Utente saveNewUtente(UtentePayload payload){

    Utente newUtente = new Utente(
            payload.username(),
            payload.email(),
            bcrypt.encode(payload.password()),
            payload.nome(),
            payload.cognome()
    );
         //per mettere di default il ruolo di user
        Ruolo ruoloUser = ruoloRepository.findByNome("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Ruolo ROLE_USER non trovato!"));

        newUtente.getRuoli().add(ruoloUser);



    Utente savedUtente = utenteRepository.save(newUtente);
        log.info("Utente " + savedUtente.getCognome() + " salvato correttamente!");
        return savedUtente;

    }

  //findByID
    public Utente findById(UUID utenteId){

        return utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException( "utente non trovato" , utenteId));

    }


public Utente findByIdAndUpdate(UUID utenteId, UtentePayload payload){

       Utente found =  utenteRepository.findById(utenteId)
               .orElseThrow(() -> new NotFoundException("Utente non trovato" , utenteId ));


       found.setUsername(payload.username());
       found.setEmail(payload.email());
       found.setPassword(payload.password());
       found.setNome(payload.nome());
       found.setCognome(payload.cognome());


    return utenteRepository.save(found);
}

//delete
public void findByIdAndDelete(UUID utenteId){

    Utente found = utenteRepository.findById(utenteId)
            .orElseThrow(() -> new NotFoundException("Utente non trovato", utenteId));

    utenteRepository.delete(found);

    log.info("utente con ID " + utenteId + " eliminato correttamente.");

}
//trova tramite email, serve per l'autenticazione.
    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con l'email  non è stato trovato"));
    }


    public void assegnaRuolo(UUID utenteId, String nomeRuolo) {
         Utente found = findById(utenteId);
         Ruolo ruolofound = ruoloRepository.findByNome(nomeRuolo)
                .orElseThrow(() -> new RuntimeException("Ruolo non trovato: " + nomeRuolo));

        found.getRuoli().add(ruolofound);
        utenteRepository.save(found);
    }


}
