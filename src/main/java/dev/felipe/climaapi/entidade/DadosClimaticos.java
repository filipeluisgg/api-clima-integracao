package dev.felipe.climaapi.entidade;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "dados_climaticos")
public class DadosClimaticos
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String local;

    @Column(name = "dia", nullable = false)
    private LocalDate dia;

    private BigDecimal longitude;

    private BigDecimal latitude;

    @Column(name = "condicao_clima")
    private String condicaoClima;

    @Column(name = "descricao_clima")
    private String descricaoClima;

    @Column(name = "temperatura_atual")
    private BigDecimal temperaturaAtual;

    @Column(name = "temperatura_minima")
    private BigDecimal temperaturaMinima;

    @Column(name = "temperatura_maxima")
    private BigDecimal temperaturaMaxima;

    private Integer umidade;

    @Column(name = "data_consulta", nullable = false)
    private OffsetDateTime dataConsulta;
}

