package dev.felipe.climaapi.excecao;

public class ConfiguracaoInvalidaException extends RuntimeException
{
    public ConfiguracaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
