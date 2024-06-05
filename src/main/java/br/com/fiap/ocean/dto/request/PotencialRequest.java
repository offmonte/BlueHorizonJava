package br.com.fiap.ocean.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record PotencialRequest(
        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @NotNull(message = "Escala é obrigatória")
        Integer escala,

        @NotNull(message = "Data é obrigatória")
        Date data
) {}
