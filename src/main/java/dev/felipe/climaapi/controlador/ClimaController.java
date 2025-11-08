package dev.felipe.climaapi.controlador;

import dev.felipe.climaapi.entidade.DadosClimaticos;
import dev.felipe.climaapi.servico.ClimaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/clima")
@RestController
public class ClimaController implements ClimaApiDocs
{
    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }


    @Override
    @PostMapping
    public ResponseEntity<DadosClimaticos> buscarEArmazenar(BigDecimal latitude, BigDecimal longitude) {
        DadosClimaticos dadosSalvos = climaService.buscarEArmazenarClima(latitude, longitude);
        return ResponseEntity.status(HttpStatus.OK).body(dadosSalvos);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<DadosClimaticos>> consultarPorLocal(@RequestParam String nomeLocal) {
        List<DadosClimaticos> dados = climaService.consultarClimaPorLocal(nomeLocal);
        return ResponseEntity.ok(dados);
    }

    @Override
    @GetMapping("/todos")
    public ResponseEntity<List<DadosClimaticos>> listarTodosOsRegistros() {
        List<DadosClimaticos> dados = climaService.listarTodos();
        return ResponseEntity.ok(dados);
    }
}
