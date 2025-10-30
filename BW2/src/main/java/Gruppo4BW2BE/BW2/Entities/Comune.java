package Gruppo4BW2BE.BW2.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "comuni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comune {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String nome;

//    @ManyToOne
//    @JoinColumn(name = "provincia_sigla", nullable = false)
//    private Provincia provincia;

    public Comune(String nome, Provincia provincia) {
        this.nome = nome;
//        this.provincia = provincia;
    }


}