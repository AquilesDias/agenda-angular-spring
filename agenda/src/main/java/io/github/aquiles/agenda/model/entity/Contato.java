package io.github.aquiles.agenda.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Contato implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String nome;
    private String email;
    private Boolean favorito;

    @Lob
    private byte[] foto;

}
