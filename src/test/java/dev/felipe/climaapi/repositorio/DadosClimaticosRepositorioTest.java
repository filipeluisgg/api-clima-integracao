package dev.felipe.climaapi.repositorio;

import dev.felipe.climaapi.entidade.DadosClimaticos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DadosClimaticosRepositorioTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DadosClimaticosRepositorio dadosClimaticosRepositorio;

    @Test
    @DisplayName("Deve encontrar uma lista de dados climáticos pelo nome do local, ignorando maiúsculas/minúsculas")
    void findByLocalIgnoreCase_QuandoLocalExiste_DeveRetornarListaDeDadosClimaticos() {
        // Arrange
        DadosClimaticos dados = new DadosClimaticos();
        dados.setLocal("Fortaleza");
        dados.setTemperaturaAtual(new BigDecimal("30.0"));
        dados.setDia(LocalDate.now());
        dados.setLatitude(new BigDecimal("-3.7184"));
        dados.setLongitude(new BigDecimal("-38.5434"));
        dados.setDataConsulta(OffsetDateTime.now());
        entityManager.persistAndFlush(dados);

        // Act
        List<DadosClimaticos> resultado = dadosClimaticosRepositorio.findByLocalIgnoreCase("fortaleza");

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getLocal()).isEqualTo("Fortaleza");
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia se o local não for encontrado")
    void findByLocalIgnoreCase_QuandoLocalNaoExiste_DeveRetornarListaVazia() {
        // Act
        List<DadosClimaticos> resultado = dadosClimaticosRepositorio.findByLocalIgnoreCase("local-inexistente");

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve encontrar um dado climático pelo nome do local e dia, ignorando maiúsculas/minúsculas")
    void findByLocalIgnoreCaseAndDia_QuandoRegistroExiste_DeveRetornarOptionalComValor() {
        // Arrange
        LocalDate hoje = LocalDate.now();
        DadosClimaticos dados = new DadosClimaticos();
        dados.setLocal("Recife");
        dados.setTemperaturaAtual(new BigDecimal("28.5"));
        dados.setDia(hoje);
        dados.setLatitude(new BigDecimal("-8.0578"));
        dados.setLongitude(new BigDecimal("-34.8829"));
        dados.setDataConsulta(OffsetDateTime.now());
        entityManager.persistAndFlush(dados);

        // Act
        Optional<DadosClimaticos> resultado = dadosClimaticosRepositorio.findByLocalIgnoreCaseAndDia("recife", hoje);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getLocal()).isEqualTo("Recife");
        assertThat(resultado.get().getDia()).isEqualTo(hoje);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio se o registro não for encontrado por local e dia")
    void findByLocalIgnoreCaseAndDia_QuandoNaoEncontra_DeveRetornarOptionalVazio() {
        // Arrange
        LocalDate hoje = LocalDate.now();
        DadosClimaticos dados = new DadosClimaticos();
        dados.setLocal("Natal");
        dados.setTemperaturaAtual(new BigDecimal("29.0"));
        dados.setDia(hoje);
        dados.setLatitude(new BigDecimal("-5.7945"));
        dados.setLongitude(new BigDecimal("-35.1994"));
        dados.setDataConsulta(OffsetDateTime.now());
        entityManager.persistAndFlush(dados);

        // Act
        Optional<DadosClimaticos> resultadoPorDiaDiferente = dadosClimaticosRepositorio.findByLocalIgnoreCaseAndDia("natal", hoje.minusDays(1));
        Optional<DadosClimaticos> resultadoPorLocalDiferente = dadosClimaticosRepositorio.findByLocalIgnoreCaseAndDia("joao pessoa", hoje);

        // Assert
        assertThat(resultadoPorDiaDiferente).isNotPresent();
        assertThat(resultadoPorLocalDiferente).isNotPresent();
    }
}
