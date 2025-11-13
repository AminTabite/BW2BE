package Gruppo4BW2BE.BW2.Security;

import Gruppo4BW2BE.BW2.Entities.Utente;
import Gruppo4BW2BE.BW2.Exceptions.UnauthorizedException;
import Gruppo4BW2BE.BW2.Services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // Esce dal filtro
        }

        String accessToken = authHeader.replace("Bearer ", "");

        try {

            jwtTools.verifyToken(accessToken);


            UUID utenteId = jwtTools.extractIdFromToken(accessToken);
            Utente found = utenteService.findById(utenteId);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    found, null, found.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (Exception e) {

            throw new UnauthorizedException("Token non valido" +
                    "");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Questo è corretto, non esegue il filtro per le rotte di login/registrazione
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}