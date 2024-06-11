package br.com.matteusmoreno.exception.exception_handler;

import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class HandleJsonbException implements ExceptionMapper<JsonbException> {


    @Override
    public Response toResponse(JsonbException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
