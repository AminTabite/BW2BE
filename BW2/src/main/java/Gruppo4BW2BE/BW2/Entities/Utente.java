package Gruppo4BW2BE.BW2.Entities;

import Gruppo4BW2BE.BW2.Enums.TipoUtente;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    @Generated
    @Setter(AccessLevel.NONE)
    UUID id;

    @Enumerated(EnumType.STRING)
    TipoUtente role;

    String username;

    String email;

    String password;

    String nome;

    String cognome;

    @Column(name = "avatar_url")
    private String avatarURL;

    @OneToMany(mappedBy = "id")
    List<Cliente> clienti;


    public Utente( String username, String email, String password, String nome, String cognome) {
        this.role = TipoUtente.USER;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
