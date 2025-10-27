package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Utente;
import Gruppo4BW2BE.BW2.Payloads.UtentePayload;
import Gruppo4BW2BE.BW2.Repositories.UtenteRepository;
import Gruppo4BW2BE.BW2.Services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    UtenteService utenteService;


    @GetMapping
    public Page<Utente> getTuttiUtenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "email") String sort
    ) {
        return utenteService.getAllClienti(page, size, sort);
    }

//ritorna un utente singolo
    @GetMapping("/{utenteId}")
    public Utente getUtenteById(@PathVariable UUID utenteId){

        return utenteService.findById(utenteId);

    }

// creazione utente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createNewUtente(@RequestBody UtentePayload payload) {

        return utenteService.saveNewUtente(payload);

    }

    // rimozione utente

    @DeleteMapping("/{utenteid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtente(@PathVariable UUID utenteId) {


        utenteService.findByIdAndDelete(utenteId);

    }




}
