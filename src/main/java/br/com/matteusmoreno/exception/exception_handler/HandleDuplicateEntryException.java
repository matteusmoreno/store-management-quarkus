package br.com.matteusmoreno.exception.exception_handler;

import br.com.matteusmoreno.exception.exception_class.DuplicateEntryException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class HandleDuplicateEntryException implements ExceptionMapper<DuplicateEntryException> {
    @Override
    public Response toResponse(DuplicateEntryException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
