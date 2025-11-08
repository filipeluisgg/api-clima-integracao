package dev.felipe.climaapi.controlador;

import dev.felipe.climaapi.dto.ErroPadraoDTO;
import dev.felipe.climaapi.entidade.DadosClimaticos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;


@Tag(name = "API de Clima", description = "Endpoints para consulta e extração de dados climáticos")
@RequestMapping("/clima")
public interface ClimaApiDocs
{
    @Operation(
            summary = "Busca dados na API externa (OpenWeather), salva no banco e retorna o registro que foi salvo.",
            description = "Busca os dados climáticos atuais da OpenWeather com base nas coordenadas. Cria um novo registro se for a primeira consulta do dia para o local, caso contrário, atualiza o registro existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados climáticos buscados e salvos com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros de requisição inválidos.", content = @Content(schema = @Schema(implementation = ErroPadraoDTO.class))),
    })
    @PostMapping
    ResponseEntity<DadosClimaticos> buscarEArmazenar(
            @Parameter(description = "Latitude (ex: -27.5895)", required = true, example = "-27.5895")
            @RequestParam BigDecimal latitude,

            @Parameter(description = "Longitude (ex: -48.5216)", required = true, example = "-48.5216")
            @RequestParam BigDecimal longitude
    );


    @Operation(
            summary = "Consulta dados salvos no banco de dados",
            description = "Consulta o banco de dados e retorna o histórico de todos os registros salvos para um local específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de registros encontrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetro 'nomeLocal' ausente na requisição.", content = @Content(schema = @Schema(implementation = ErroPadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum registro encontrado para o local informado.", content = @Content(schema = @Schema(implementation = ErroPadraoDTO.class)))
    })
    @GetMapping
    ResponseEntity<List<DadosClimaticos>> consultarPorLocal(
            @Parameter(description = "Nome do local (bairro/cidade)", required = true, example = "Trindade")
            @RequestParam String nomeLocal
    );


    @Operation(
            summary = "Lista todos os registros",
            description = "Retorna todos os registros de dados climáticos que foram salvos no banco de dados. Retorna uma lista vazia caso não haja nenhum registro."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de todos os registros retornada com sucesso.")
    })
    @GetMapping("/todos")
    ResponseEntity<List<DadosClimaticos>> listarTodosOsRegistros();
}
