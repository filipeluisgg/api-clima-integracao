package dev.felipe.climaapi.controlador;

import dev.felipe.climaapi.entidade.DadosClimaticos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Tag(name = "API de Clima", description = "Endpoints para consulta e extração de dados climáticos")
@RequestMapping("/clima")
public interface ClimaApiDocs
{
    @Operation(
            summary = "Busca dados na API externa e salva no banco",
            description = "Demonstra a extração de dados da API OpenWeather. Cria um novo registro se for o primeiro do dia para o local pesquisado, se não for, atualiza o registro existente."
    )
    @PostMapping
    ResponseEntity<DadosClimaticos> buscarEArmazenar(
            @Parameter(description = "Latitude (ex: -27.5895)", required = true)
            @RequestParam BigDecimal latitude,

            @Parameter(description = "Longitude (ex: -48.5216)", required = true)
            @RequestParam BigDecimal longitude
    );


    @Operation(
            summary = "Consulta dados salvos no banco de dados",
            description = "Consulta o banco de dados e retorna o histórico de todos os registros salvos para um local específico."
    )
    @GetMapping
    ResponseEntity<List<DadosClimaticos>> consultarPorLocal(
            @Parameter(description = "Nome do local (bairro/cidade, ex: 'Trindade')", required = true)
            @RequestParam String nomeLocal
    );


    @Operation(
            summary = "Lista todos os registros",
            description = "Retorna todos os registros de dados climáticos que foram salvos no banco de dados."
    )
    @GetMapping("/todos")
    ResponseEntity<List<DadosClimaticos>> listarTodosOsRegistros();
}
