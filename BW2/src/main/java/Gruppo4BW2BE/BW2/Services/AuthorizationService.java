package Gruppo4BW2BE.BW2.Services;


import Gruppo4BW2BE.BW2.Entities.Utente;
import Gruppo4BW2BE.BW2.Exceptions.UnauthorizedException;
import Gruppo4BW2BE.BW2.Payloads.LoginPayload;
import Gruppo4BW2BE.BW2.Security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String CheckCredentialAndDoToken(LoginPayload body) {

        Utente found = this.utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword()))
        {
            return jwtTools.createToken(found);
        } else{
            throw new UnauthorizedException("credenziali errate");
        }

    }


}
