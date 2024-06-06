package br.com.matteusmoreno.exception;

public class EmailTemplateReadException extends RuntimeException{

    public EmailTemplateReadException(String message) {
        super(message);
    }
}
