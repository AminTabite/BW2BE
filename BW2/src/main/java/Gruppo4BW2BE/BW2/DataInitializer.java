package Gruppo4BW2BE.BW2;

import Gruppo4BW2BE.BW2.Services.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CsvImportService csvImportService;

    @Autowired
    private Gruppo4BW2BE.BW2.Repositories.ProvinciaRepository provinciaRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Avvio inizializzazione dati...");


        if (provinciaRepository.count() == 0) {
            System.out.println("Importazione province...");
            csvImportService.importProvince("province-italiane.csv");
        } else {
            System.out.println("Province già presenti nel database.");
        }

        System.out.println("Importazione comuni...");
        csvImportService.importComuni("comuni-italiani.csv");

        System.out.println("Inizializzazione dati completata.");
    }
}