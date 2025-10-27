package Gruppo4BW2BE.BW2.Controllers;

import Gruppo4BW2BE.BW2.Entities.Utente;
import Gruppo4BW2BE.BW2.Exceptions.ValidationsException;
import Gruppo4BW2BE.BW2.Payloads.LoginPayload;
import Gruppo4BW2BE.BW2.Payloads.TokenPayload;
import Gruppo4BW2BE.BW2.Payloads.UtentePayload;
import Gruppo4BW2BE.BW2.Services.AuthorizationService;
import Gruppo4BW2BE.BW2.Services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public TokenPayload login(@RequestBody LoginPayload body){
        return new TokenPayload(authorizationService.CheckCredentialAndDoToken(body));
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createUtente(@RequestBody @Validated UtentePayload payload, BindingResult validationResult){
        if (validationResult.hasErrors()) {

            throw new ValidationsException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.utenteService.saveNewUtente(payload);


    }



}



