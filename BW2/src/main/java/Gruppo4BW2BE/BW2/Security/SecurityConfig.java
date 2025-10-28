package Gruppo4BW2BE.BW2.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    //Bean che crypta le password sul db
    @Bean
    public PasswordEncoder getBCrypt(){

        return new BCryptPasswordEncoder(12);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin(formLogin -> formLogin.disable());
        //escludo log in perche' lo faremo su front end
        httpSecurity.csrf(csrf -> csrf.disable());

        //protezione da csrf al momento non serve

        httpSecurity.sessionManagement(sessions ->
                sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(req -> req
                .requestMatchers("/auth/**").permitAll() //endpoint disponibili con autenticazione


                //endpoint disponibili SOLO DOPO AUTENTICAZIONE
                .requestMatchers("/clienti/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/fatture/**").hasAuthority( "ADMIN")
                        .requestMatchers("/fatture/{id}").hasAuthority("ADMIN")
                        .requestMatchers("/clienti/**").hasAuthority( "ADMIN")
                        .requestMatchers("/clienti/**").hasAuthority( "USER")
                        .requestMatchers("/utenti/**").hasAuthority("ADMIN")

//                .requestMatchers("//**").hasRole("USER")
                .anyRequest().authenticated()
        );

        return  httpSecurity.build();

    }

}
