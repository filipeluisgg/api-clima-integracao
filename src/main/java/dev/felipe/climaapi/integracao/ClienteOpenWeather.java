package dev.felipe.climaapi.integracao;

import dev.felipe.climaapi.dto.RespostaClimaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "openweather", url = "${openweather.api.url}")
public interface ClienteOpenWeather
{
    /**
     * Busca os dados climáticos por coordenadas (latitude e longitude).
     * Os parâmetros 'units=metric' (Celsius) e 'lang=pt_br' são
     * definidos diretamente no @GetMapping por serem constantes.
     * @return Um record RespostaClimaDTO com os dados da API.
     */
    @GetMapping("/weather?units=metric&lang=pt_br")
    RespostaClimaDTO buscarClimaPorCoordenadas(
            @RequestParam("lat") BigDecimal latitude,
            @RequestParam("lon") BigDecimal longitude,
            @RequestParam("appid") String apiKey
    );
}
