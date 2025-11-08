package dev.felipe.climaapi.mapper;

import dev.felipe.climaapi.dto.RespostaClimaDTO;
import dev.felipe.climaapi.entidade.DadosClimaticos;
import org.springframework.stereotype.Component;
import java.time.LocalDate; // <-- Importe
import java.time.OffsetDateTime;

@Component
public class ClimaMapper
{
    public DadosClimaticos mapearParaEntidade(RespostaClimaDTO dto, LocalDate dia) {
        DadosClimaticos entidade = new DadosClimaticos();
        entidade.setDia(dia);
        entidade.setLocal(dto.nomeLocal());
        entidade.setDataConsulta(OffsetDateTime.now()); // <-- ADICIONE AQUI
        this.atualizarDados(entidade, dto);
        return entidade;
    }

    public void atualizarDados(DadosClimaticos entidade, RespostaClimaDTO dto) {
        entidade.setLongitude(dto.coordenada().lon());
        entidade.setLatitude(dto.coordenada().lat());
        entidade.setTemperaturaAtual(dto.principal().temperatura());
        entidade.setTemperaturaMinima(dto.principal().temperaturaMinima());
        entidade.setTemperaturaMaxima(dto.principal().temperaturaMaxima());
        entidade.setUmidade(dto.principal().umidade());
        entidade.setDataConsulta(OffsetDateTime.now());

        if (dto.clima() != null && !dto.clima().isEmpty()) {
            entidade.setCondicaoClima(dto.clima().getFirst().condicao());
            entidade.setDescricaoClima(dto.clima().getFirst().descricao());
        }
        // Nota: 'data_consulta' e 'dia' não são atualizados,
        // pois representam a criação do registro.
    }
}