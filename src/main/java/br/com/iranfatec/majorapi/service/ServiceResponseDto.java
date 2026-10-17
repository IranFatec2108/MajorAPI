package br.com.iranfatec.majorapi.service;

import java.math.BigDecimal;

public record ServiceResponseDto(

        Long id,
        String name,
        BigDecimal suggestedPrice,
        boolean active
) {
}
