package dev.felipe.climaapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RespostaClimaDTO
(
        @JsonProperty("name")
        String nomeLocal,

        @JsonProperty("coord")
        CoordenadaDTO coordenada,

        @JsonProperty("main")
        PrincipalDTO principal,

        @JsonProperty("weather")
        List<ClimaInfoDTO> clima
) {}
