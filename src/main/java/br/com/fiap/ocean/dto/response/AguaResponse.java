package br.com.fiap.ocean.dto.response;

public record AguaResponse(
        Long id,
        String cidade,
        Double ph,
        Double oxigenio,
        Double nitrato,
        Double fosfato,
        Double microplastico,
        Double salinidade,
        Boolean qualidadeDaAgua
) {}
