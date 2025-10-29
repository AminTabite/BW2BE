package Gruppo4BW2BE.BW2.Payloads;

import Gruppo4BW2BE.BW2.Entities.Cliente;
import Gruppo4BW2BE.BW2.Entities.StatoFattura;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FatturaPayload(

        String numero,
        LocalDate data,
        BigDecimal importo,
        Cliente cliente,
        StatoFattura stato






) {
}
