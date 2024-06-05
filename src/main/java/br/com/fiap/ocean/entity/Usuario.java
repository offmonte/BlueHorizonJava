package br.com.fiap.ocean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USUARIO")
    @SequenceGenerator(name = "SQ_USUARIO", sequenceName = "SQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "DENOMINACAO")
    private String denominacao;

    @Column(name = "NASCIMENTO")
    private LocalDate nascimento;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            })
    @JoinColumn(
            name = "CREDENCIAL",
            referencedColumnName = "ID_CREDENCIAL",
            foreignKey = @ForeignKey(
                    name = "FK_USUARIO_CREDENCIAL"
            )
    )
    private  Credencial credencial;
}
