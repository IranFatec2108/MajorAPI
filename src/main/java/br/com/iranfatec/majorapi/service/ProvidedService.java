package br.com.iranfatec.majorapi.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_services")
public class ProvidedService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @PositiveOrZero
    @Column(name = "suggested_price", nullable = false)
    private BigDecimal suggestedPrice;

    @Column(name = "active", nullable = false)
    private boolean Active = true;


}
