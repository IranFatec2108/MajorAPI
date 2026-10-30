package br.com.iranfatec.majorapi.attendance;

import br.com.iranfatec.majorapi.service.ProvidedService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_attendances", indexes = @Index(name = "idx_attendance_date", columnList = "date_time"))
public class Attendance {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name_snapshot", nullable = false, length = 255)
    private String serviceNameSnapshot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provided_service_id", nullable = false)
    private ProvidedService providedService;

    @Column(name = "amount_paid", nullable = false, precision = 19, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "date_time", nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime dateTime;

    @Column(name = "valid", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean valid = true;
}
