package br.com.matteusmoreno.service_order;

import br.com.matteusmoreno.service_order.service_order_request.CreateServiceOrderRequest;
import br.com.matteusmoreno.service_order.service_order_response.ServiceOrderDetailsResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.bson.types.ObjectId;
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

    @GET
    @Path("/details/{id}")
    public Response details(@PathParam("id") ObjectId id) {
        ServiceOrder serviceOrder = serviceOrderService.serviceOrderDetails(id);

        return Response.ok(new ServiceOrderDetailsResponse(serviceOrder)).build();
    }

    @PATCH
    @Path("/start-service/{id}")
    public Response start(@PathParam("id") ObjectId id) {
        ServiceOrder serviceOrder = serviceOrderService.startServiceOrder(id);

        return Response.ok(new ServiceOrderDetailsResponse(serviceOrder)).build();
    }

    @PATCH
    @Path("/complete-service/{id}")
    public Response complete(@PathParam("id") ObjectId id) {
        ServiceOrder serviceOrder = serviceOrderService.completeServiceOrder(id);

        return Response.ok(new ServiceOrderDetailsResponse(serviceOrder)).build();
    }

    @PATCH
    @Path("cancel-service/{id}")
    public Response cancel(@PathParam("id") ObjectId id) {
        ServiceOrder serviceOrder = serviceOrderService.cancelServiceOrder(id);

        return Response.ok(new ServiceOrderDetailsResponse(serviceOrder)).build();
    }
}
