package br.com.matteusmoreno.controller;

import br.com.matteusmoreno.domain.ServiceOrder;
import br.com.matteusmoreno.request.CreateServiceOrderRequest;
import br.com.matteusmoreno.response.ServiceOrderDetailsResponse;
import br.com.matteusmoreno.service.ServiceOrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.UUID;

@Path("/service_orders")
public class ServiceOrderController {

    @Inject
    ServiceOrderService serviceOrderService;

    @POST
    @Path("/create")
    public Response create(@RequestBody @Valid CreateServiceOrderRequest request) {
        ServiceOrder serviceOrder = serviceOrderService.createServiceOrder(request);

        return Response.ok(new ServiceOrderDetailsResponse(serviceOrder)).build();
    }

    @GET
    @Path("/details/{id}")
    public Response detailsById(@PathParam("id") UUID id) {
        ServiceOrder serviceOrder = serviceOrderService.serviceOrderDetails(id);

        return Response.ok(new ServiceOrderDetailsResponse(serviceOrder)).build();
    }
}
