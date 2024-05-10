package br.com.matteusmoreno.controller;

import br.com.matteusmoreno.domain.Employee;
import br.com.matteusmoreno.request.CreateEmployeeRequest;
import br.com.matteusmoreno.request.UpdateEmployeeRequest;
import br.com.matteusmoreno.response.EmployeeDetailsResponse;
import br.com.matteusmoreno.service.EmployeeService;
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

@Path("/employees")
public class EmployeeController {

    @Inject
    EmployeeService employeeService;

    @POST
    @Path("/create")
    public Response create(@RequestBody @Valid CreateEmployeeRequest request, @Context UriInfo uriInfo) {
        Employee employee = employeeService.createEmployee(request);

        // Construa a URI para o recurso criado usando UriBuilder
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(employee.getId().toString());
        URI uri = uriBuilder.build();

        return Response.created(uri).entity(new EmployeeDetailsResponse(employee)).build();
    }

    @GET
    @Path("/details/{id}")
    public Response details(@PathParam("id") UUID id) {
        Employee employee = employeeService.employeeDetails(id);

        return Response.ok(new EmployeeDetailsResponse(employee)).build();
    }

    @PUT
    @Path("/update")
    public Response update(@RequestBody @Valid UpdateEmployeeRequest request) {
        Employee employee = employeeService.updateEmployee(request);

        return Response.ok(new EmployeeDetailsResponse(employee)).build();
    }

    @DELETE
    @Path("/disable/{id}")
    public Response disable(@PathParam("id") UUID id) {
        employeeService.disableEmployee(id);

        return Response.noContent().build();
    }

    @PATCH
    @Path("/enable/{id}")
    public Response enable(@PathParam("id") UUID id) {
        Employee employee = employeeService.enableCustomer(id);

        return Response.ok(new EmployeeDetailsResponse(employee)).build();
    }
}
