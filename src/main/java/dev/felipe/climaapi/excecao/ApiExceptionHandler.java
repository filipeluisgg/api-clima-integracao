package dev.felipe.climaapi.excecao;

import dev.felipe.climaapi.dto.ErroPadraoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(LocalNaoEncontradoException.class)
    public ResponseEntity<ErroPadraoDTO> tratarLocalNaoEncontradoException(LocalNaoEncontradoException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ErroPadraoDTO erro = new ErroPadraoDTO(
                "NotFoundError",
                ex.getMessage(),
                "Verifique o nome do local fornecido e tente novamente. Caso não " +
                      "tenha registros deste local, busque dados na API externa e salve " +
                      "no banco através da documentação do endpoint:  POST /clima",
                httpStatus.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroPadraoDTO> tratarParametroInvalidoException(MissingServletRequestParameterException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String mensagemDeErro = String.format("O parâmetro '%s' é obrigatório.", ex.getParameterName());

        ErroPadraoDTO erro = new ErroPadraoDTO(
                "ValidationError",
                mensagemDeErro,
                "Verifique os parâmetros fornecidos e tente novamente.",
                status.value()
        );
        return ResponseEntity.status(status).body(erro);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroPadraoDTO> tratarMetodoNaoSuportadoException(HttpRequestMethodNotSupportedException ex)
    {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        List<String> metodosSeguros = ex.getSupportedMethods() != null
                                        ? Arrays.asList(ex.getSupportedMethods())
                                        : Collections.emptyList();

        String metodosSuportadosString = String.join(", ", metodosSeguros);

        ErroPadraoDTO erro = new ErroPadraoDTO(
                "MethodNotAllowedError",
                "O método " + ex.getMethod() + " não é suportado para este recurso.",
                "Use um dos métodos suportados: " + metodosSuportadosString,
                status.value()
        );
        return ResponseEntity.status(status).body(erro);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErroPadraoDTO> tratarCaminhoNaoEncontrado(NoHandlerFoundException ex)
    {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String mensagem = String.format("O recurso '%s' não foi encontrado.", ex.getRequestURL());

        ErroPadraoDTO erro = new ErroPadraoDTO(
                "PathNotFoundError",
                mensagem,
                "Verifique o endpoint (URL) da requisição e tente novamente.",
                status.value()
        );
        return ResponseEntity.status(status).body(erro);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadraoDTO> handleGenericException(Exception ex)
    {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErroPadraoDTO erro = new ErroPadraoDTO(
                "InternalServerError",
                "Ocorreu um erro inesperado no servidor. Tente novamente mais tarde.",
                "Ocorreu um erro inesperado no processamento. Entre em contato com o suporte.",
                status.value()
        );
        return ResponseEntity.status(status).body(erro);
    }
}
