package br.com.fiap.ocean.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_AGUA")
public class Agua {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AGUA")
    @SequenceGenerator(name = "SQ_AGUA", sequenceName = "SQ_AGUA", allocationSize = 1)
    @Column(name = "ID_AGUA")
    private Long id;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "PH")
    private Double ph;

    @Column(name = "OXIGENIO")
    private Double oxigenio;

    @Column(name = "NITRATO")
    private Double nitrato;

    @Column(name = "FOSFATO")
    private Double fosfato;

    @Column(name = "MICROPLASTICO")
    private Double microplastico;

    @Column(name = "SALINIDADE")
    private Double salinidade;

    @Column(name = "QUALIDADE_DA_AGUA")
    private Boolean qualidadeDaAgua;

}
