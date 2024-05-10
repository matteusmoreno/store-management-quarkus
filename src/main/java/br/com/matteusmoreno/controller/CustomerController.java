package br.com.matteusmoreno.controller;

import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.request.CreateCustomerRequest;
import br.com.matteusmoreno.request.UpdateCustomerRequest;
import br.com.matteusmoreno.response.CustomerDetailsResponse;
import br.com.matteusmoreno.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/customers")
public class CustomerController {

    @Inject
    CustomerService customerService;


    @POST
    @Path("/create")
    public Response createCustomer(@RequestBody @Valid CreateCustomerRequest request, @Context UriInfo uriInfo) {
        Customer customer = customerService.createCustomer(request);

        // Construa a URI para o recurso criado usando UriBuilder
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(customer.getId().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(new CustomerDetailsResponse(customer)).build();
    }

    @GET
    @Path("/details/{id}")
    public Response details(@PathParam("id") UUID id) {
        Customer customer = customerService.customerDetails(id);

        return Response.ok(new CustomerDetailsResponse(customer)).build();
    }

    @PUT
    @Path("/update")
    public Response update(@RequestBody @Valid UpdateCustomerRequest request) {
        Customer customer = customerService.updateCustomer(request);

        return Response.ok(new CustomerDetailsResponse(customer)).build();
    }

    @DELETE
    @Path("/disable/{id}")
    public Response disable(@PathParam("id") UUID id) {
        customerService.disableCustomer(id);

        return Response.noContent().build();
    }

    @PATCH
    @Path("/enable/{id}")
    public Response enable(@PathParam("id") UUID id) {
       Customer customer = customerService.enableCustomer(id);

        return Response.ok(new CustomerDetailsResponse(customer)).build();
    }
}
