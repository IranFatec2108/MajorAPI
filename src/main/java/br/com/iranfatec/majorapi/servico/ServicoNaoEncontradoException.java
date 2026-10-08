package br.com.iranfatec.majorapi.servico;

public class ServicoNaoEncontradoException extends RuntimeException {
    public ServicoNaoEncontradoException(String message) {
        super(message);
    }
}
