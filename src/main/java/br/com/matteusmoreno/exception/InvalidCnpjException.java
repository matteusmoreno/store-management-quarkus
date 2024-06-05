package br.com.matteusmoreno.exception;

public class InvalidCnpjException extends RuntimeException {

    public InvalidCnpjException(String message) {
        super(message);
    }
}
