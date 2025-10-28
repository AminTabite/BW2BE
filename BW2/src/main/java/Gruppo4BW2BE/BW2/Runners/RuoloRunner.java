package Gruppo4BW2BE.BW2.Runners;

import Gruppo4BW2BE.BW2.Entities.Ruolo;
import Gruppo4BW2BE.BW2.Repositories.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RuoloRunner implements CommandLineRunner {

    @Autowired
    RuoloRepository ruoloRepository;




    public RuoloRunner(RuoloRepository ruoloRepository) {
        this.ruoloRepository = ruoloRepository;
    }

    @Override
    public void run(String... args) {
        if (ruoloRepository.findByNome("ROLE_USER").isEmpty()) {
            ruoloRepository.save(new Ruolo(null, "ROLE_USER"));
            System.out.println(" Creato ruolo: ROLE_USER");
        }

        if (ruoloRepository.findByNome("ROLE_ADMIN").isEmpty()) {
            ruoloRepository.save(new Ruolo(null, "ROLE_ADMIN"));
            System.out.println(" Creato ruolo: ROLE_ADMIN");
        }
    }
}
