package br.com.matteusmoreno.controller;

import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.repository.CustomerRepository;
import br.com.matteusmoreno.request.CreateCostumerRequest;
import br.com.matteusmoreno.response.CustomerDetailsResponse;
import br.com.matteusmoreno.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response.Status create(CreateCostumerRequest request) {
        Customer customer = customerService.createCustomer(request);
        return Response.Status.CREATED;
    }

    @GET
    public List<Customer> list() {
        Customer customer = new Customer("Matteus");
        listCustomers.add(customer);

        return listCustomers;
    }
}
