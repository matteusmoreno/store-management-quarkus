package br.com.matteusmoreno.controller;

import br.com.matteusmoreno.domain.ServiceOrder;
import br.com.matteusmoreno.request.CreateServiceOrderRequest;
import br.com.matteusmoreno.request.UpdateServiceOrderRequest;
import br.com.matteusmoreno.response.service_order_response.ServiceOrderDetailsResponse;
import br.com.matteusmoreno.service.ServiceOrderService;
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

@Path("/service_orders")
public class ServiceOrderController {

    private final ServiceOrderService serviceOrderService;

    @Inject
    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @POST
    @Path("/create")
    public Response create(@RequestBody @Valid CreateServiceOrderRequest request, @Context UriInfo uriInfo) {
        ServiceOrderDetailsResponse serviceOrder = serviceOrderService.createServiceOrder(request);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(serviceOrder.id().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(serviceOrder).build();
    }

    @GET
    @Path("/details/{id}")
    public Response detailsById(@PathParam("id") UUID id) {
        ServiceOrder serviceOrder = serviceOrderService.serviceOrderDetails(id);

        return Response.ok(serviceOrder).build();
    }

    @PUT
    @Path("/update")
    public Response update(@RequestBody @Valid UpdateServiceOrderRequest request) {
        ServiceOrderDetailsResponse serviceOrder = serviceOrderService.updateServiceOrder(request);

        return Response.ok(serviceOrder).build();
    }

    @PATCH
    @Path("/start-service/{id}")
    public Response start(@PathParam("id") UUID id) {
        ServiceOrder serviceOrder = serviceOrderService.startService(id);

        return Response.ok(serviceOrder).build();
    }

    @PATCH
    @Path("/complete-service/{id}")
    public Response complete(@PathParam("id") UUID id) {
        ServiceOrder serviceOrder = serviceOrderService.completeService(id);

        return Response.ok(serviceOrder).build();
    }

    @PATCH
    @Path("/cancel-service/{id}")
    public Response cancel(@PathParam("id") UUID id) {
        ServiceOrder serviceOrder = serviceOrderService.cancelService(id);

        return Response.ok(serviceOrder).build();
    }
}
