package br.com.fiap.ocean.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UsuarioResponse(
        Long id,
        String denominacao,
        CredencialResponse credencial,
        LocalDate nascimento
) {}
