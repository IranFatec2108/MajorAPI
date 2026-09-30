package br.com.iranfatec.majorapi.servico;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "nome", nullable = false)
    private String nome;

    @PositiveOrZero
    @Column(name = "preco_sugerido", nullable = false)
    private BigDecimal precoSugerido;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;


}
