package Gruppo4BW2BE.BW2.Payloads;

import Gruppo4BW2BE.BW2.Entities.Comune;

public record IndirizzoPayload(

        String via,
        String civico,
        String localita,
        int cap,
        Comune comune



) {
}
