package br.com.fiap.ocean.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_CREDENCIAL",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_EMAIL", columnNames = {"EMAIL"}),
                @UniqueConstraint(name = "UK_SENHA", columnNames = {"SENHA"})
        }
)
public class Credencial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CREDENCIAL")
    @SequenceGenerator(name = "SQ_CREDENCIAL", sequenceName = "SQ_CREDENCIAL", allocationSize = 1)
    @Column(name = "ID_CREDENCIAL")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;

}
