package dev.felipe.climaapi.servico;

import dev.felipe.climaapi.dto.RespostaClimaDTO;
import dev.felipe.climaapi.entidade.DadosClimaticos;
import dev.felipe.climaapi.integracao.ClienteOpenWeather;
import dev.felipe.climaapi.repositorio.DadosClimaticosRepositorio;
import org.springframework.beans.factory.annotation.Value;
import dev.felipe.climaapi.mapper.ClimaMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ClimaService
{
    private final DadosClimaticosRepositorio dadosClimaticosRepositorio;
    private final ClienteOpenWeather clienteOpenWeather;
    private final ClimaMapper climaMapper;

    @Value("${openweather.api.key}")
    private String apiKey;

    public ClimaService(DadosClimaticosRepositorio repositorio,
                        ClienteOpenWeather clienteOpenWeather,
                        ClimaMapper climaMapper) {
        this.dadosClimaticosRepositorio = repositorio;
        this.clienteOpenWeather = clienteOpenWeather;
        this.climaMapper = climaMapper;
    }


    /**
     * @param latitude  Latitude para a consulta.
     * @param longitude Longitude para a consulta.
     * @return A entidade DadosClimaticos que foi salva no banco.
     */
    public DadosClimaticos buscarEArmazenarClima(BigDecimal latitude, BigDecimal longitude)
    {
        RespostaClimaDTO respostaClimaDTO = clienteOpenWeather.buscarClimaPorCoordenadas(
                latitude,
                longitude,
                apiKey
        );

        String local = respostaClimaDTO.nomeLocal();
        LocalDate hoje = LocalDate.now();

        Optional<DadosClimaticos> existenteOpt = dadosClimaticosRepositorio.findByLocalIgnoreCaseAndDia(local, hoje);

        if (existenteOpt.isPresent()) {
            DadosClimaticos entidadeExistente = existenteOpt.get();
            climaMapper.atualizarDados(entidadeExistente, respostaClimaDTO);
            return dadosClimaticosRepositorio.save(entidadeExistente);
        } else {
            DadosClimaticos novaEntidade = climaMapper.mapearParaEntidade(respostaClimaDTO, hoje);
            return dadosClimaticosRepositorio.save(novaEntidade);
        }
    }


    /**
     * @param nomeLocal O nome do nomeLocal (bairro/cidade) a ser consultado.
     * @return Uma lista de registros históricos do banco.
     */
    public List<DadosClimaticos> consultarClimaPorLocal(String nomeLocal) {
        return dadosClimaticosRepositorio.findByLocalIgnoreCase(nomeLocal);
    }
}
