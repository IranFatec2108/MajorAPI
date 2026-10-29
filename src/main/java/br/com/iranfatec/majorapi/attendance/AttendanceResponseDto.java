package br.com.iranfatec.majorapi.attendance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AttendanceResponseDto(

        Long id,
        String serviceNameSnapshot,
        BigDecimal amountPaid,
        LocalDateTime dateTime,
        Boolean valid) {
}
