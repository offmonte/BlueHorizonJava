package br.com.fiap.ocean.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CredencialRequest(

        @NotNull(message = "Email é obrigatório")
        @Email(message = "Email inválido!")
        String email,

        @NotNull(message = "Senha é obrigatório")
        @Pattern(
                message = "A senha não atende aos requisitos",
                regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,16}" // Corrigida a expressão regular
        )
        String senha
) {
}
