package Gruppo4BW2BE.BW2.Payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class ErrorsPayload {

    private String messaggio;
    private LocalDateTime oraevento;




}

