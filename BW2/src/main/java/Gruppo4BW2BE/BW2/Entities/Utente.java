package Gruppo4BW2BE.BW2.Entities;

import Gruppo4BW2BE.BW2.Enums.TipoUtente;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor

public class Utente {
    @Id
    @Generated
    @Setter(AccessLevel.NONE)
    UUID utenteId;

    @Enumerated(EnumType.STRING)
    TipoUtente role;

    String username;

    String email;

    String password;

    String nome;

    String cognome;

    @Column(name = "avatar_url")
    private String avatarURL;

    public Utente( String username, String email, String password, String nome, String cognome, String avatarURL) {
        this.role = TipoUtente.USER;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }
}
