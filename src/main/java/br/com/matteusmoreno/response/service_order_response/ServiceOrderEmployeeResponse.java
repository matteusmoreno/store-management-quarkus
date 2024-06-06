package br.com.matteusmoreno.response.service_order_response;

import br.com.matteusmoreno.constant.EmployeeRole;
import br.com.matteusmoreno.domain.Employee;

import java.util.UUID;

public record ServiceOrderEmployeeResponse(

        UUID id,
        String name,
        EmployeeRole role) {

    public ServiceOrderEmployeeResponse(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getRole());
    }
}
