package br.com.matteusmoreno.payable_account;

import br.com.matteusmoreno.payable_account.payable_account_request.CreatePayableAccountRequest;
import br.com.matteusmoreno.payable_account.payable_account_response.PayableAccountDetailsResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;

@Path("/payable_accounts")
public class PayableAccountController {

    private final PayableAccountService payableAccountService;

    @Inject
    public PayableAccountController(PayableAccountService payableAccountService) {
        this.payableAccountService = payableAccountService;
    }

    @POST
    @Path("/create")
    public Response create(@RequestBody @Valid CreatePayableAccountRequest request, @Context UriInfo uriInfo) {
        PayableAccount payableAccount = payableAccountService.createPayableAccount(request);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(payableAccount.getId().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(new PayableAccountDetailsResponse(payableAccount)).build();
    }

    @GET
    @Path("/details/{id}")
    public Response details(@PathParam("id") ObjectId id) {
        PayableAccount payableAccount = payableAccountService.payableAccountDetails(id);

        return Response.ok(new PayableAccountDetailsResponse(payableAccount)).build();
    }

    @PATCH
    @Path("/pay-payable-account/{id}")
    public Response pay(@PathParam("id") ObjectId id) {
        PayableAccount payableAccount = payableAccountService.payPayableAccount(id);

        return Response.ok(new PayableAccountDetailsResponse(payableAccount)).build();
    }

    @PATCH
    @Path("/cancel-payable-account/{id}")
    public Response cancel(@PathParam("id") ObjectId id) {
        PayableAccount payableAccount = payableAccountService.cancelPayableAccount(id);

        return Response.ok(new PayableAccountDetailsResponse(payableAccount)).build();
    }
}
