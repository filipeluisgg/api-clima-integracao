package dev.felipe.climaapi.excecao;

public class LocalNaoEncontradoException extends RuntimeException
{
    public LocalNaoEncontradoException(String nomeLocal) {
        super("Dados climáticos não encontrados para o local: " + nomeLocal);
    }
}
