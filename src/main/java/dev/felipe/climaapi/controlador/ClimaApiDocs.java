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
            description = "Demonstra a extração de dados da API OpenWeather e o salvamento no banco de dados local."
    )
    @PostMapping("/buscar")
    ResponseEntity<DadosClimaticos> buscarEArmazenar(
            @Parameter(description = "Latitude (ex: -23.5505)", required = true)
            @RequestParam BigDecimal latitude,

            @Parameter(description = "Longitude (ex: -46.6818)", required = true)
            @RequestParam BigDecimal longitude
    );


    @Operation(
            summary = "Consulta dados salvos no banco por local",
            description = "Consulta o banco de dados local e retorna o histórico de todos os registros salvos para um local específico."
    )
    @GetMapping("/consultar/{nomeLocal}")
    ResponseEntity<List<DadosClimaticos>> consultarPorLocal(
            @Parameter(description = "Nome do local (bairro/cidade, ex: 'Campo Grande')", required = true)
            @PathVariable String nomeLocal
    );
}
