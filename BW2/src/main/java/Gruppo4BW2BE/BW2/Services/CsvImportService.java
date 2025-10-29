package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Comune;
import Gruppo4BW2BE.BW2.Entities.Provincia;
import Gruppo4BW2BE.BW2.Repositories.ComuneRepository;
import Gruppo4BW2BE.BW2.Repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CsvImportService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ComuneRepository comuneRepository;

    // Metodo per importare le province
    @Transactional
    public void importProvince(String filePath) {
        List<Provincia> provinceDaSalvare = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            reader.readLine(); // Salta l'intestazione

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 3) {
                    String sigla = data[0].trim();
                    String nome = data[1].trim();
                    String regione = data[2].trim();

                    // Controlla se la provincia esiste già (basandosi su ID)
                    if (!provinciaRepository.existsById(sigla)) {
                        Provincia provincia = new Provincia(sigla, nome, regione);
                        provinceDaSalvare.add(provincia);
                    } else {
                        System.out.println("Provincia con sigla " + sigla + " già esistente. Saltata.");
                    }
                } else {
                    System.err.println("Riga malformattata (province): " + line);
                }
            }
            // Salva tutte le province
            provinciaRepository.saveAll(provinceDaSalvare);
            System.out.println("Importazione province completata. Importate " + provinceDaSalvare.size() + " nuove province.");

        } catch (Exception e) {
            System.err.println("Errore durante l'importazione delle province: " + e.getMessage());
            e.printStackTrace();

        }
    }

    // Metodo per importare i comuni
    @Transactional
    public void importComuni(String filePath) {
        List<Comune> comuniDaSalvare = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("[;\t]");

                if (data.length >= 4) {

                    String nomeComune = data[2].trim();
                    String nomeProvincia = data[3].trim();
                    Optional<Provincia> provinciaOpt = provinciaRepository.findByNomeIgnoreCase(nomeProvincia);

                    if (provinciaOpt.isPresent()) {
                        Provincia provincia = provinciaOpt.get();
                        Comune comune = new Comune(nomeComune, provincia);
                        comuniDaSalvare.add(comune);

                    } else {
                        System.err.println("Provincia non trovata per il comune '" + nomeComune + "': " + nomeProvincia);
                    }
                } else {
                    System.err.println("Riga malformattata (comuni): " + line);
                }
            }
            // Salva tutti i nuovi comuni
            comuneRepository.saveAll(comuniDaSalvare);
            System.out.println("Importazione comuni completata. Creati " + comuniDaSalvare.size() + " nuovi comuni.");

        } catch (Exception e) {
            System.err.println("Errore durante l'importazione dei comuni: " + e.getMessage());
            e.printStackTrace();

        }
    }
}