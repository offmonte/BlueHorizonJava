package br.com.fiap.ocean.dto.response;

import lombok.Builder;

@Builder
public record CredencialResponse(
        Long id,
        String email
) {}
