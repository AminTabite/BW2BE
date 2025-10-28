package Gruppo4BW2BE.BW2.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ruolo {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome; // es: "ROLE_USER", "ROLE_ADMIN"
}