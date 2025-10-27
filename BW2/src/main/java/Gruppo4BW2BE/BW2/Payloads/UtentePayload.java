package Gruppo4BW2BE.BW2.Payloads;

import jakarta.validation.constraints.NotBlank;

public record UtentePayload(
        @NotBlank

        String username,
        String email, String password,
        String nome,
        String cognome
) {


//    String username;
//
//    String email;
//
//    String password;
//
//    String nome;
//
//    String cognome;

}
