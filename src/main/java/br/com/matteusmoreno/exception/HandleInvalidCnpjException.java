package br.com.matteusmoreno.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class HandleInvalidCnpjException implements ExceptionMapper<InvalidCnpjException>, ResponseExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(InvalidCnpjException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }

    @Override
    public RuntimeException toThrowable(Response response) {
        if (response.getStatus() == 400) {
            return new InvalidCnpjException("Invalid CNPJ");
        }
        return null;
    }
}
