package dev.felipe.climaapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClimaInfoDTO
(
        @JsonProperty("main")
        String condicao,

        @JsonProperty("description")
        String descricao
) {}
