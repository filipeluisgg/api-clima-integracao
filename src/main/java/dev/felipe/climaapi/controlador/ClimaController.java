package dev.felipe.climaapi.controlador;

import dev.felipe.climaapi.entidade.DadosClimaticos;
import dev.felipe.climaapi.servico.ClimaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


@RestController
public class ClimaController implements ClimaApiDocs
{
    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }


    @Override
    public ResponseEntity<DadosClimaticos> buscarEArmazenar(BigDecimal latitude, BigDecimal longitude) {
        DadosClimaticos dadosSalvos = climaService.buscarEArmazenarClima(latitude, longitude);
        return ResponseEntity.status(HttpStatus.CREATED).body(dadosSalvos);
    }

    @Override
    public ResponseEntity<List<DadosClimaticos>> consultarPorLocal(String nomeLocal) {
        List<DadosClimaticos> dados = climaService.consultarClimaPorLocal(nomeLocal);
        return ResponseEntity.ok(dados);
    }
}
