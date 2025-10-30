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
public class StatoFattura {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String statoFattura;

    public void setStatoFattura(String nuovoNome) {
    }
}
