package dev.felipe.climaapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ErroPadraoDTO(

        @JsonProperty("name")
        String nome,

        @JsonProperty("message")
        String mensagem,

        @JsonProperty("action")
        String acao,

        @JsonProperty("status_code")
        Integer statusCode
) {}
