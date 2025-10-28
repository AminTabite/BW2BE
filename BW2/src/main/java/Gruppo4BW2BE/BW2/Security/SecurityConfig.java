package Gruppo4BW2BE.BW2.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    //Bean che crypta le password sul db
    @Bean
    public PasswordEncoder getBCrypt(){

        return new BCryptPasswordEncoder(12);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Specifica l'origine esatta del frontend
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        // Specifica i metodi HTTP consentiti
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Specifica gli header consentiti
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Applica questa configurazione a tutte le rotte
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin(formLogin -> formLogin.disable());
        //escludo log in perche' lo faremo su front end
        httpSecurity.csrf(csrf -> csrf.disable());

        //protezione da csrf al momento non serve

        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        httpSecurity.sessionManagement(sessions ->
                sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(req -> req
                .requestMatchers("/auth/**").permitAll() //endpoint disponibili con autenticazione


                //endpoint disponibili SOLO DOPO AUTENTICAZIONE
                .requestMatchers("/clienti/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/fatture/**").hasAnyRole( "ADMIN")
                        .requestMatchers("/fatture/{id}").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/clienti/**").hasAnyRole("USER", "ADMIN")

//                .requestMatchers("//**").hasRole("USER")
                .anyRequest().authenticated()
        );

        return  httpSecurity.build();

    }

}
