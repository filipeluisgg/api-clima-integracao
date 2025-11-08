package dev.felipe.climaapi.controlador;

import dev.felipe.climaapi.entidade.DadosClimaticos;
import dev.felipe.climaapi.excecao.LocalNaoEncontradoException;
import dev.felipe.climaapi.servico.ClimaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClimaController.class)
class ClimaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClimaService climaService;

    @Test
    @DisplayName("POST /clima - Deve retornar 200 OK com os dados salvos")
    void buscarEArmazenar_ComCoordenadasValidas_DeveRetornarStatus200() throws Exception {
        // Arrange
        BigDecimal latitude = new BigDecimal("-23.5505");
        BigDecimal longitude = new BigDecimal("-46.6333");
        DadosClimaticos dadosSalvos = new DadosClimaticos();
        dadosSalvos.setId(1L);
        dadosSalvos.setLocal("São Paulo");
        dadosSalvos.setTemperaturaAtual(new BigDecimal("22.5"));

        given(climaService.buscarEArmazenarClima(latitude, longitude)).willReturn(dadosSalvos);

        // Act & Assert
        mockMvc.perform(post("/clima")
                        .param("latitude", latitude.toString())
                        .param("longitude", longitude.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.local", is("São Paulo")));
    }

    @Test
    @DisplayName("GET /clima - Deve retornar 200 OK com a lista de dados para um local existente")
    void consultarPorLocal_QuandoLocalExiste_DeveRetornarStatus200() throws Exception {
        // Arrange
        DadosClimaticos dados = new DadosClimaticos();
        dados.setLocal("Curitiba");
        given(climaService.consultarClimaPorLocal("Curitiba")).willReturn(List.of(dados));

        // Act & Assert
        mockMvc.perform(get("/clima")
                        .param("nomeLocal", "Curitiba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].local", is("Curitiba")));
    }

    @Test
    @DisplayName("GET /clima - Deve retornar 404 Not Found para um local inexistente")
    void consultarPorLocal_QuandoLocalNaoExiste_DeveRetornarStatus404() throws Exception {
        // Arrange
        String localInexistente = "Local Inexistente";
        given(climaService.consultarClimaPorLocal(localInexistente))
                .willThrow(new LocalNaoEncontradoException(localInexistente));

        // Act & Assert
        mockMvc.perform(get("/clima")
                        .param("nomeLocal", localInexistente))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /clima/todos - Deve retornar 200 OK com a lista de todos os registros")
    void listarTodosOsRegistros_DeveRetornarStatus200() throws Exception {
        // Arrange
        given(climaService.listarTodos()).willReturn(List.of(new DadosClimaticos(), new DadosClimaticos()));

        // Act & Assert
        mockMvc.perform(get("/clima/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
