package br.com.fiap.ocean.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AguaRequest(
        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @NotNull(message = "PH é obrigatório")
        Double ph,

        @NotNull(message = "Oxigênio é obrigatório")
        Double oxigenio,

        @NotNull(message = "Nitrato é obrigatório")
        Double nitrato,

        @NotNull(message = "Fosfato é obrigatório")
        Double fosfato,

        @NotNull(message = "Microplástico é obrigatório")
        Double microplastico,

        @NotNull(message = "Salinidade é obrigatória")
        Double salinidade,

        @NotNull(message = "Qualidade da água é obrigatória")
        Boolean qualidadeDaAgua
) {}
