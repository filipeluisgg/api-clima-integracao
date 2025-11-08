package dev.felipe.climaapi.servico;

import dev.felipe.climaapi.dto.CoordenadaDTO;
import dev.felipe.climaapi.dto.PrincipalDTO;
import dev.felipe.climaapi.dto.RespostaClimaDTO;
import dev.felipe.climaapi.entidade.DadosClimaticos;
import dev.felipe.climaapi.excecao.ConfiguracaoInvalidaException;
import dev.felipe.climaapi.excecao.LocalNaoEncontradoException;
import dev.felipe.climaapi.integracao.ClienteOpenWeather;
import dev.felipe.climaapi.mapper.ClimaMapper;
import dev.felipe.climaapi.repositorio.DadosClimaticosRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClimaServiceTest
{
    @Mock
    private DadosClimaticosRepositorio dadosClimaticosRepositorio;
    @Mock
    private ClienteOpenWeather clienteOpenWeather;
    @Mock
    private ClimaMapper climaMapper;

    @InjectMocks
    private ClimaService climaService;

    private RespostaClimaDTO respostaClimaDTO;
    private DadosClimaticos dadosClimaticos;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @BeforeEach
    void setUp() {
        latitude = new BigDecimal("-23.5505");
        longitude = new BigDecimal("-46.6333");

        respostaClimaDTO = new RespostaClimaDTO(
                "São Paulo",
                new CoordenadaDTO(longitude, latitude),
                new PrincipalDTO(new BigDecimal("25.5"), new BigDecimal("20.0"), new BigDecimal("30.0"), 70),
                Collections.emptyList()
        );

        dadosClimaticos = new DadosClimaticos();
        dadosClimaticos.setId(1L);
        dadosClimaticos.setLocal("São Paulo");
    }

    @Test
    @DisplayName("Deve criar uma nova entidade quando não houver registro para o local no dia")
    void buscarEArmazenarClima_QuandoNaoExisteRegistro_DeveCriarUmNovo() {
        // Arrange
        ReflectionTestUtils.setField(climaService, "apiKey", "valid-key");
        when(clienteOpenWeather.buscarClimaPorCoordenadas(latitude, longitude, "valid-key")).thenReturn(respostaClimaDTO);
        when(dadosClimaticosRepositorio.findByLocalIgnoreCaseAndDia("São Paulo", LocalDate.now())).thenReturn(Optional.empty());
        when(climaMapper.mapearParaEntidade(respostaClimaDTO, LocalDate.now())).thenReturn(dadosClimaticos);
        when(dadosClimaticosRepositorio.save(any(DadosClimaticos.class))).thenReturn(dadosClimaticos);

        // Act
        DadosClimaticos resultado = climaService.buscarEArmazenarClima(latitude, longitude);

        // Assert
        assertThat(resultado).isNotNull();
        verify(dadosClimaticosRepositorio).save(dadosClimaticos);
        verify(climaMapper, never()).atualizarDados(any(), any());
    }

    @Test
    @DisplayName("Deve atualizar uma entidade existente quando já houver registro para o local no dia")
    void buscarEArmazenarClima_QuandoJaExisteRegistro_DeveAtualizarExistente() {
        // Arrange
        ReflectionTestUtils.setField(climaService, "apiKey", "valid-key");
        when(clienteOpenWeather.buscarClimaPorCoordenadas(latitude, longitude, "valid-key")).thenReturn(respostaClimaDTO);
        when(dadosClimaticosRepositorio.findByLocalIgnoreCaseAndDia("São Paulo", LocalDate.now())).thenReturn(Optional.of(dadosClimaticos));
        when(dadosClimaticosRepositorio.save(any(DadosClimaticos.class))).thenReturn(dadosClimaticos);

        // Act
        DadosClimaticos resultado = climaService.buscarEArmazenarClima(latitude, longitude);

        // Assert
        assertThat(resultado).isNotNull();
        verify(climaMapper).atualizarDados(dadosClimaticos, respostaClimaDTO);
        verify(dadosClimaticosRepositorio).save(dadosClimaticos);
        verify(climaMapper, never()).mapearParaEntidade(any(), any());
    }

    @Test
    @DisplayName("Deve retornar uma lista de dados climáticos ao consultar por um local existente")
    void consultarClimaPorLocal_QuandoLocalExiste_DeveRetornarLista() {
        // Arrange
        when(dadosClimaticosRepositorio.findByLocalIgnoreCase("São Paulo")).thenReturn(List.of(dadosClimaticos));

        // Act
        List<DadosClimaticos> resultado = climaService.consultarClimaPorLocal("São Paulo");

        // Assert
        assertThat(resultado).containsExactly(dadosClimaticos);
    }

    @Test
    @DisplayName("Deve lançar LocalNaoEncontradoException ao consultar por um local inexistente")
    void consultarClimaPorLocal_QuandoLocalNaoExiste_DeveLancarExcecao() {
        // Arrange
        String localInexistente = "Local Inexistente";
        when(dadosClimaticosRepositorio.findByLocalIgnoreCase(localInexistente)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(LocalNaoEncontradoException.class, () -> {
            climaService.consultarClimaPorLocal(localInexistente);
        });
    }
}
