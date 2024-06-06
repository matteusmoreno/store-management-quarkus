package br.com.matteusmoreno.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class HandleEmailTemplateReadException implements ExceptionMapper<EmailTemplateReadException> {
    @Override
    public Response toResponse(EmailTemplateReadException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(e.getMessage())
                .build();
    }
}
