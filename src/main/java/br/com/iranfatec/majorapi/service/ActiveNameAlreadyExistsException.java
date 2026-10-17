package br.com.iranfatec.majorapi.service;

public class ActiveNameAlreadyExistsException extends RuntimeException {
    public ActiveNameAlreadyExistsException(String message) {
        super(message);
    }
}
