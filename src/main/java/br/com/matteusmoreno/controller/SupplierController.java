package br.com.matteusmoreno.controller;

import br.com.matteusmoreno.domain.Supplier;
import br.com.matteusmoreno.request.CreateSupplierRequest;
import br.com.matteusmoreno.request.UpdateSupplierRequest;
import br.com.matteusmoreno.response.SupplierDetailsResponse;
import br.com.matteusmoreno.service.SupplierService;
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

@Path("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Inject
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @POST
    @Path("/create")
    public Response create(@RequestBody @Valid CreateSupplierRequest request, @Context UriInfo uriInfo) {
        Supplier supplier = supplierService.createSupplier(request);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(supplier.getId().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(new SupplierDetailsResponse(supplier)).build();
    }

    @GET
    @Path("/details/{id}")
    public Response details(@PathParam("id") UUID id) {
        Supplier supplier = supplierService.supplierDetails(id);

        return Response.ok(new SupplierDetailsResponse(supplier)).build();
    }

    @PUT
    @Path("/update")
    public Response update(@RequestBody @Valid UpdateSupplierRequest request) {
        Supplier supplier = supplierService.updateSupplier(request);

        return Response.ok(new SupplierDetailsResponse(supplier)).build();
    }

    @DELETE
    @Path("/disable/{id}")
    public Response disable(@PathParam("id") UUID id) {
        supplierService.disableSupplier(id);

        return Response.noContent().build();
    }

    @PATCH
    @Path("/enable/{id}")
    public Response enable(@PathParam("id") UUID id) {
        Supplier supplier = supplierService.enableSupplier(id);

        return Response.ok(new SupplierDetailsResponse(supplier)).build();
    }
}
