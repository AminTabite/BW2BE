package Gruppo4BW2BE.BW2.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "province")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Provincia {

    @Id
    @Column(length = 2)
    private String sigla;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String regione;
//
//    @OneToMany(mappedBy = "provincia")
//    @ToString.Exclude
//    private List<Comune> comuni;

    public Provincia(String sigla, String nome, String regione) {
        this.sigla = sigla;
        this.nome = nome;
        this.regione = regione;
    }
}