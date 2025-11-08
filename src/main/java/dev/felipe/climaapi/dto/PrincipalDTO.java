package dev.felipe.climaapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record PrincipalDTO
(
        @JsonProperty("temp")
        BigDecimal temperatura,

        @JsonProperty("temp_min")
        BigDecimal temperaturaMinima,

        @JsonProperty("temp_max")
        BigDecimal temperaturaMaxima,

        @JsonProperty("humidity")
        Integer umidade
) {}
