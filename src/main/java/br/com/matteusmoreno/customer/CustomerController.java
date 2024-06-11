package br.com.matteusmoreno.customer;

import br.com.matteusmoreno.customer.customer_request.CreateCustomerRequest;
import br.com.matteusmoreno.customer.customer_request.UpdateCustomerRequest;
import br.com.matteusmoreno.customer.customer_response.CustomerDetailsResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.UUID;

@Path("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Inject
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @POST
    @Path("/create")
    public Response createCustomer(@RequestBody @Valid CreateCustomerRequest request, @Context UriInfo uriInfo) {
        Customer customer = customerService.createCustomer(request);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(customer.getId().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(new CustomerDetailsResponse(customer)).build();
    }

    @GET
    @Path("/details/{id}")
    public Response detailsById(@PathParam("id") UUID id) {
        Customer customer = customerService.customerDetailsById(id);

        return Response.ok(new CustomerDetailsResponse(customer)).build();
    }

    @GET
    @Path("/details-by-neighborhood/{neighborhood}")
    public Response detailsByNeighborhood(@PathParam("neighborhood") String neighborhood) {

        var customers = customerService.customerDetailsByNeighborhood(neighborhood);

        return Response.ok(customers).build();
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
