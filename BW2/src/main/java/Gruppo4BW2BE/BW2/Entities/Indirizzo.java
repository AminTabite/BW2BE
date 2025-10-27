package Gruppo4BW2BE.BW2.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@ToString
public class Indirizzo {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String via;

    @Column(nullable = false)
    private String civico;

    @Column(nullable = false)
    private String localita;

    @Column(nullable = false)
    private int cap;

    @Column(nullable = false)
    private String comune;

    public Indirizzo() {

    }

    public Indirizzo(UUID id, String via, String civico, String localita, int cap, String comune) {
        this.id = id;
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }

}
