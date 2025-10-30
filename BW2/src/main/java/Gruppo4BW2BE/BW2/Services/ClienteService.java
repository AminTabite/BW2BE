package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Cliente;
import Gruppo4BW2BE.BW2.Entities.Indirizzo;
import Gruppo4BW2BE.BW2.Exceptions.BadRequestException;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
import Gruppo4BW2BE.BW2.Payloads.ClientePayload;
import Gruppo4BW2BE.BW2.Repositories.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private IndirizzoService indirizzoService;

    // Recuperare tutti i clienti paginati
    public Page<Cliente> getAllClienti(int pageNumber, int pageSize, String sortBy) {
        // Oggetto Pageable per la paginazione
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        // Chiamiamo findAll
        return clienteRepository.findAll(pageable);
    }

    // Salva nuovo Cliente
    public Cliente saveNuovoCliente(ClientePayload payload) {

        // Controlliamo se la partita IVA esiste
        clienteRepository.findByPartitaIva(payload.partitaIva()) // Usa i metodi del record
                .ifPresent(clienteEsistente -> {
                    throw new BadRequestException("Partita IVA " + clienteEsistente.getPartitaIva() + " già in uso.");
                });

        // Controlliamo se l'Email esiste
        clienteRepository.findByEmail(payload.email()) // Usa i metodi del record
                .ifPresent(clienteEsistente -> {
                    throw new BadRequestException("Email " + clienteEsistente.getEmail() + " già in uso.");
                });

        Indirizzo sedeLegale = indirizzoService.findById(payload.indirizzoSedeLegaleId());
        Indirizzo sedeOperativa = indirizzoService.findById(payload.indirizzoSedeOperativaId());

        // Mappiamo il Payload sull'Entità
        Cliente nuovoCliente = new Cliente();
        nuovoCliente.setRagioneSociale(payload.ragioneSociale());
        nuovoCliente.setPartitaIva(payload.partitaIva());
        nuovoCliente.setEmail(payload.email());
        nuovoCliente.setFatturatoAnnuale(payload.fatturatoAnnuale());
        nuovoCliente.setPec(payload.pec());
        nuovoCliente.setTelefono(payload.telefono());
        nuovoCliente.setEmailContatto(payload.emailContatto());
        nuovoCliente.setNomeContatto(payload.nomeContatto());
        nuovoCliente.setCognomeContatto(payload.cognomeContatto());
        nuovoCliente.setTelefonoContatto(payload.telefonoContatto());
        nuovoCliente.setLogoAziendale(payload.logoAziendale());
        nuovoCliente.setTipoCliente(payload.tipoCliente());

        // Assegna gli indirizzi direttamente dal payload
        nuovoCliente.setIndirizzoSedeLegale(sedeLegale);
        nuovoCliente.setIndirizzoSedeOperativa(sedeOperativa);

        // Imposta le date
        nuovoCliente.setDataInserimento(LocalDate.now());
        nuovoCliente.setDataUltimoContatto(payload.dataUltimoContatto() != null ? payload.dataUltimoContatto() : LocalDate.now());

        return clienteRepository.save(nuovoCliente);
    }

    // Trova un cliente tramite il suo ID
    public Cliente findById(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente"));
    }

    // Elimina Cliente
    public void findByIdAndDelete(UUID id) {
        Cliente clienteTrovato = this.findById(id);
        clienteRepository.delete(clienteTrovato);
    }

}
