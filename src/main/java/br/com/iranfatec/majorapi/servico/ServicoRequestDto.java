package br.com.iranfatec.majorapi.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ServicoRequestDto(

    @NotBlank(message = "O nome do serviço é obrigatório")
    String nome,

    @NotNull(message = "O preço sugerido é obrigatório")
    @PositiveOrZero (message = "O preço sugerido deve ser zero ou positivo")
    BigDecimal precoSugerido
) {}
