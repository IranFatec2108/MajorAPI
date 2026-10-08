package br.com.iranfatec.majorapi.servico;

public class NomeAtivoJaExistenteException extends RuntimeException {
    public NomeAtivoJaExistenteException(String message) {
        super(message);
    }
}
