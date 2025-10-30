package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Cliente;
import Gruppo4BW2BE.BW2.Payloads.ClientePayload;
import Gruppo4BW2BE.BW2.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clienti")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    // GET (READ) Lista Paginata
    // /clienti?page=0&size=10&sort=id
    @GetMapping
    public Page<Cliente> getTuttiIClienti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return clienteService.getAllClienti(page, size, sort);
    }

    // GET Singolo Cliente da ID
    // /clienti/{id}
    @GetMapping("/{clienteId}")
    public Cliente getClienteById(@PathVariable UUID clienteId) {
        return clienteService.findById(clienteId);
    }

    // POST Nuovo Cliente
    // /clienti
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente creaNuovoCliente(@RequestBody ClientePayload clientePayload) {
        return clienteService.saveNuovoCliente(clientePayload);
    }

    // DELETE Cancella Cliente da ID
    // clienti/{id}
    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaCliente(@PathVariable UUID clienteId) {
        clienteService.findByIdAndDelete(clienteId);
    }

}
