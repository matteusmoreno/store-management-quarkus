package br.com.matteusmoreno.client.viacep;

import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://viacep.com.br")
@Default
public interface ViaCepClient {

    @GET
    @Path("/ws/{zipcode}/json/")
    ViaCepResponse getAddress(@PathParam("zipcode") String zipcode);
}
