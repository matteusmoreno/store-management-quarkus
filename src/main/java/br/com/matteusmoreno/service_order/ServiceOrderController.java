package br.com.matteusmoreno.service_order;

import br.com.matteusmoreno.service_order.service_order_request.CreateServiceOrderRequest;
import br.com.matteusmoreno.service_order.service_order_response.ServiceOrderDetailsResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;

@Path("/service_orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceOrderController {

    private final ServiceOrderService serviceOrderService;

    @Inject
    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @POST
    @Path("/create")
    public Response create(@RequestBody @Valid CreateServiceOrderRequest request, @Context UriInfo uriInfo) {
        ServiceOrder serviceOrder = serviceOrderService.createServiceOrder(request);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(serviceOrder.getId().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(new ServiceOrderDetailsResponse(serviceOrder)).build();
    }
}
