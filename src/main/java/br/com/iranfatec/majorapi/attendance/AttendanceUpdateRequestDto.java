package br.com.iranfatec.majorapi.attendance;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AttendanceUpdateRequestDto(


        @Positive(message = "O ID do serviço deve ser um número positivo")
        Long providedServiceId,

        @PositiveOrZero(message = "O valor pago deve ser zero ou positivo")
        @Digits(integer = 17, fraction = 2, message = "O valor pago deve ter até 2 casas decimais")
        BigDecimal amountPaid)
 {}
