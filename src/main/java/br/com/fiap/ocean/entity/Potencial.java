package br.com.fiap.ocean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_POTENCIAL")
public class Potencial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_POTENCIAL")
    @SequenceGenerator(name = "SQ_POTENCIAL", sequenceName = "SQ_POTENCIAL", allocationSize = 1)
    @Column(name = "ID_POTENCIAL")
    private Long id;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESCALA")
    private Integer escala;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA")
    private Date data;
}
