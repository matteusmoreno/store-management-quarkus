package br.com.matteusmoreno.exception.exception_class;

public class EmailTemplateReadException extends RuntimeException{

    public EmailTemplateReadException(String message) {
        super(message);
    }
}
