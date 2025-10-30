package Gruppo4BW2BE.BW2.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Indirizzo {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String via;

    @Column(nullable = false)
    private String civico;

    @Column()
    private String localita;

    @Column(nullable = false)
    private int cap;

    @Column()
    private String comune;


    public Indirizzo(String via, String civico, String localita, int cap, String comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}