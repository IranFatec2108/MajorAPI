package br.com.iranfatec.majorapi.attendance;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AttendanceCreateRequestDto(
        @NotNull(message = "O ID do serviço é obrigatório")
        @Positive(message = "O ID do serviço deve ser um número positivo")
        Long providedServiceId,

        @Digits(integer = 17, fraction = 2, message = "O valor pago deve ter até 2 casas decimais")
        @NotNull(message = "O valor pago é obrigatório")
        @PositiveOrZero(message = "O valor pago deve ser zero ou positivo")
        BigDecimal amountPaid) {
}
