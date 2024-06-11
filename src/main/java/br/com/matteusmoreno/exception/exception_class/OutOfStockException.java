package br.com.matteusmoreno.exception.exception_class;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String message) {
        super(message);
    }
}
