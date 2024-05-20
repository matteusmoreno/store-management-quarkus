package br.com.matteusmoreno.controller;

import br.com.matteusmoreno.service.LoginGoogleResourceService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginGoogleResource {

    private final LoginGoogleResourceService loginGoogleResourceService;

    @Inject
    public LoginGoogleResource(LoginGoogleResourceService loginGoogleResourceService) {
        this.loginGoogleResourceService = loginGoogleResourceService;
    }


    @GET
    @Path("/user")
    @Authenticated
    public Response user() {

        var newToken = loginGoogleResourceService.getJwtToken();

        return Response.ok(newToken).build();
    }

    @GET
    @Path("/customerTest")
    @RolesAllowed("customer")
    public String helloCustomer() {
        return "OLÁ CLIENTE";
    }

    @GET
    @Path("/employeeTest")
    @RolesAllowed("employee")
    public String helloEmployee() {
        return "OLÁ EMPREGADO";
    }

}
