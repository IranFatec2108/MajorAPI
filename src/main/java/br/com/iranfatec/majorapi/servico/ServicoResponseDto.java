package br.com.iranfatec.majorapi.servico;

import java.math.BigDecimal;

public record ServicoResponseDto(

        Long id,
        String nome,
        BigDecimal precoSugerido,
        boolean ativo
) {
}
