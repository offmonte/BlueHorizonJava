package br.com.fiap.ocean.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioRequest(

        @Size(min = 2, max = 25)
        @NotNull(message = "Nome é Obrigatório")
        String denominacao,


        @NotNull(message = "A Data de nascimento é Obrigatório")
        LocalDate nascimento,

        @Valid
        @NotNull(message = "O Login deve ser informado")
        CredencialRequest credencial

) {
}