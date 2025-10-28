package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Ruolo;
import Gruppo4BW2BE.BW2.Services.RuoloService;
import Gruppo4BW2BE.BW2.Services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ruoli")
public class RuoloController {

    @Autowired
    private RuoloService ruoloService;

    @Autowired
    private UtenteService utenteService;



    @GetMapping
    public List<Ruolo> getAllRuoli() {
        return ruoloService.getAllRuoli();
    }


    @PostMapping
    public Ruolo createRuolo(@RequestParam String nome) {
        return ruoloService.createRuolo(nome);
    }

    // serve per assegnare un ruolo esistente a un utente
    @PostMapping("/sign")
    public String assegnaRuolo(
            @RequestParam UUID utenteId,
            @RequestParam String nomeRuolo
    )
    {
        utenteService.assegnaRuolo(utenteId, nomeRuolo);
        return "Ruolo " + nomeRuolo + " assegnato all'utente con ID: " + utenteId;
    }
}
