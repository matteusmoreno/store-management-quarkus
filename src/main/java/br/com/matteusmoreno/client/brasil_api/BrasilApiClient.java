package br.com.matteusmoreno.client.brasil_api;

import br.com.matteusmoreno.exception.HandleInvalidCnpjException;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://brasilapi.com.br/api/cnpj/v1")
@Default
@RegisterProvider(HandleInvalidCnpjException.class)
public interface BrasilApiClient {

    @GET
    @Path("/{cnpj}")
    BrasilApiResponse getSupplier(@PathParam("cnpj") String cnpj);
}
