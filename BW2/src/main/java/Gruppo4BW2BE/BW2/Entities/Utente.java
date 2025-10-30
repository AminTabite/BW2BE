package Gruppo4BW2BE.BW2.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor

public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    UUID id;


    String username;

    String email;

    String password;

    String nome;

    String cognome;

    @Column(name = "avatar_url")
    private String avatarURL;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "utenti_ruoli",
            joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "id")
    )


    private Set<Ruolo> ruolo = new HashSet<>();


    public Utente(String username, String email, String password, String nome, String cognome, Ruolo ruolo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo.add(ruolo) ;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.ruolo.stream().map(ruolo -> new SimpleGrantedAuthority(ruolo.toString())).
                collect(Collectors.toSet());
    }
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
