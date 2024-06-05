package br.com.matteusmoreno.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.NoSuchElementException;

@Provider
public class HandleNoSuchElementException implements ExceptionMapper<NoSuchElementException> {

    @Override
    public Response toResponse(NoSuchElementException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity("Entity not found")
                .build();
    }
}
