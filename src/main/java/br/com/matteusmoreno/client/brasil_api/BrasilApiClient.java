package br.com.matteusmoreno.client.brasil_api;

import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://brasilapi.com.br/api/cnpj/v1")
@Default
public interface BrasilApiClient {

    @GET
    @Path("/{cnpj}")
    BrasilApiResponse getSupplier(@PathParam("cnpj") String cnpj);
}
