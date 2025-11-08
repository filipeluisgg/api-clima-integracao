package dev.felipe.climaapi.mapper;

import dev.felipe.climaapi.dto.RespostaClimaDTO;
import dev.felipe.climaapi.entidade.DadosClimaticos;
import org.springframework.stereotype.Component;


@Component
public class ClimaMapper
{
    public DadosClimaticos mapearParaEntidade(RespostaClimaDTO dto) {
        DadosClimaticos entidade = new DadosClimaticos();

        entidade.setLocal(dto.nomeCidade());
        entidade.setLongitude(dto.coordenada().lon());
        entidade.setLatitude(dto.coordenada().lat());

        entidade.setTemperaturaAtual(dto.principal().temperatura());
        entidade.setTemperaturaMinima(dto.principal().temperaturaMinima());
        entidade.setTemperaturaMaxima(dto.principal().temperaturaMaxima());
        entidade.setUmidade(dto.principal().umidade());

        //Pega a primeira descrição do clima (se existir)
        if (dto.clima() != null && !dto.clima().isEmpty()) {
            entidade.setCondicaoClima(dto.clima().getFirst().condicao());
            entidade.setDescricaoClima(dto.clima().getFirst().descricao());
        }

        return entidade;
    }
}
