package br.com.matteusmoreno.exception.exception_handler;

import br.com.matteusmoreno.exception.exception_class.OutOfStockException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class HandleOutOfStockException implements ExceptionMapper<OutOfStockException> {
    @Override
    public Response toResponse(OutOfStockException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
