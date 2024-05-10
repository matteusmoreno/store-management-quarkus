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

@Path("/customers")
public class CustomerController {

    @Inject
    CustomerService customerService;

    List<Customer> listCustomers = new ArrayList<>();

    @POST
    @Transactional
    public Response createCustomer(@RequestBody @Valid CreateCustomerRequest request, @Context UriInfo uriInfo) {
        Customer customer = customerService.createCustomer(request);

        // Construa a URI para o recurso criado usando UriBuilder
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(customer.getId().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(new CustomerDetailsResponse(customer)).build();
    }

    @GET
    @Path("/{id}")
    public Response details(@PathParam("id") Long id) {
        Customer customer = customerService.customerDetails(id);

        return Response.ok(new CustomerDetailsResponse(customer)).build();
    }

    @PUT
    @Transactional
    public Response update(@RequestBody UpdateCustomerRequest request) {
        Customer customer = customerService.updateCustomer(request);

        return Response.ok(new CustomerDetailsResponse(customer)).build();
    }
}
