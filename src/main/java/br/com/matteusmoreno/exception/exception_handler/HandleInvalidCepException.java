package br.com.matteusmoreno.exception.exception_handler;

import br.com.matteusmoreno.exception.exception_class.InvalidCepException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class HandleInvalidCepException implements ExceptionMapper<InvalidCepException> {

    @Override
    public Response toResponse(InvalidCepException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
