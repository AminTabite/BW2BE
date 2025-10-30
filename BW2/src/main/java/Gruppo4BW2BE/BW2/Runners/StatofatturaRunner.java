package Gruppo4BW2BE.BW2.Runners;

import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import Gruppo4BW2BE.BW2.Repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StatofatturaRunner implements CommandLineRunner {



    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    @Override
    public void run(String... args) {

        creaStatoSeNonEsiste("PAGATA");
        creaStatoSeNonEsiste("NON_PAGATA");
        creaStatoSeNonEsiste("IN_CORSO");

    }

    private void creaStatoSeNonEsiste(String stato) {
        if (statoFatturaRepository.findByStatoFattura(stato).isEmpty()) {
            statoFatturaRepository.save(new StatoFattura(null, stato));
            System.out.println(" Creato stato fattura: " + stato);
        }
    }

}
