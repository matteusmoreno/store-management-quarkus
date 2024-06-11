package br.com.matteusmoreno.exception.exception_class;

public class InvalidCnpjException extends RuntimeException {

    public InvalidCnpjException(String message) {
        super(message);
    }
}
