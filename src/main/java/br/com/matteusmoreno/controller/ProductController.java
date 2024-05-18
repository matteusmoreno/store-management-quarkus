package br.com.matteusmoreno.controller;

import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.request.CreateProductRequest;
import br.com.matteusmoreno.request.UpdateProductRequest;
import br.com.matteusmoreno.response.ProductDetailsResponse;
import br.com.matteusmoreno.service.ProductService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;

@Path("/products")
public class ProductController {

    private final ProductService productService;

    @Inject
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @POST
    @Path("/create")
    public Response create(@RequestBody @Valid CreateProductRequest request, @Context UriInfo uriInfo) {
        Product product = productService.createProduct(request);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(product.getId().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(new ProductDetailsResponse(product)).build();
    }

    @GET
    @Path("/details/{id}")
    public Response details(@PathParam("id") Long id) {
        Product product = productService.productDetails(id);

        return Response.ok(new ProductDetailsResponse(product)).build();
    }

    @PUT
    @Path("/update")
    public Response update(@RequestBody @Valid UpdateProductRequest request) {
        Product product = productService.updateProduct(request);

        return Response.ok(new ProductDetailsResponse(product)).build();
    }

    @DELETE
    @Path("/disable/{id}")
    public Response disable(@PathParam("id") Long id) {
        productService.disableProduct(id);

        return Response.noContent().build();
    }

    @PATCH
    @Path("/enable/{id}")
    public Response enable(@PathParam("id") Long id) {
        Product product = productService.enableProduct(id);

        return Response.ok(new ProductDetailsResponse(product)).build();
    }
}
