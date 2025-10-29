package Gruppo4BW2BE.BW2.Payloads;

import Gruppo4BW2BE.BW2.Entities.Indirizzo;
import Gruppo4BW2BE.BW2.Enums.TipoCliente;

import java.time.LocalDate;

public record ClientePayload(

        String ragioneSociale,
        String partitaIva,
        String email,
        LocalDate dataInserimento,
        LocalDate dataUltimoContatto,
        Double fatturatoAnnuale,
        String pec,
        String telefono,
        String emailContatto,
        String nomeContatto,
        String cognomeContatto,
        String telefonoContatto,
        String logoAziendale,
        TipoCliente tipoCliente,
        Indirizzo indirizzoSedeLegale,
        Indirizzo indirizzoSedeOperativa





) {
}
