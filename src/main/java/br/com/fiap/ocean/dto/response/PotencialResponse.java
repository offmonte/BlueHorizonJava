package br.com.fiap.ocean.dto.response;

import java.util.Date;

public record PotencialResponse(
        Long id,
        String cidade,
        Integer escala,
        Date data
) {}
