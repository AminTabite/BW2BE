package Gruppo4BW2BE.BW2.Services;

import Gruppo4BW2BE.BW2.Entities.Cliente;
import Gruppo4BW2BE.BW2.Exceptions.BadRequestException;
import Gruppo4BW2BE.BW2.Exceptions.NotFoundException;
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

    // Recuperare tutti i clienti paginati
    public Page<Cliente> getAllClienti(int pageNumber, int pageSize, String sortBy) {
        // Oggetto Pageable per la paginazione
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        // Chiamiamo findAll
        return clienteRepository.findAll(pageable);
    }

    // Salva nuovo Cliente
    public Cliente saveNuovoCliente(Cliente nuovoCliente) {

        // Controlliamo se la partita IVA esiste
        clienteRepository.findByPartitaIva(nuovoCliente.getPartitaIva())
                .ifPresent(clienteEsistente -> {
                    throw new BadRequestException("Partita IVA " + clienteEsistente.getPartitaIva() + " già in uso.");
                });

        // Controlliamo se l'Email esiste
        clienteRepository.findByEmail(nuovoCliente.getEmail())
                .ifPresent(clienteEsistente -> {
                    throw new BadRequestException("Email " + clienteEsistente.getEmail() + " già in uso.");
                });
        nuovoCliente.setDataInserimento(LocalDate.now());
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
