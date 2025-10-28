package Gruppo4BW2BE.BW2.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


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

    @OneToMany(mappedBy = "id")
    List<Cliente> clienti;

    @ManyToMany
    @JoinTable(
            name = "utenti_ruoli",
            joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "id")
    )

    private List<Ruolo> ruoli = new ArrayList<>();


    public Utente( String username, String email, String password, String nome, String cognome) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli.stream()
                .map(ruolo -> (GrantedAuthority) () -> ruolo.getNome())
                .toList();
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
