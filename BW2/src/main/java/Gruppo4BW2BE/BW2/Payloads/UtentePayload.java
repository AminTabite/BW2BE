package Gruppo4BW2BE.BW2.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UtentePayload(
        @NotBlank
        @Size(min = 3, max = 20, message = "username deve avere almeno 3 caratteri e max 20")
        String username,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        @Size(min = 3, max = 20, message = "nome deve avere almeno 3 caratteri e max 20")
        String nome,
        @NotBlank
        @Size(min = 3, max = 20, message = "cognome deve avere almeno 3 caratteri e max 20")
        String cognome
) {



}
