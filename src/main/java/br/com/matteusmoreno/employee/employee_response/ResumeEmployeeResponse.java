package br.com.matteusmoreno.employee.employee_response;

import br.com.matteusmoreno.employee.Employee;
import br.com.matteusmoreno.employee.constant.EmployeeRole;

public record ResumeEmployeeResponse(
        String name,
        String employeeRole) {

    public ResumeEmployeeResponse(Employee employee) {
        this(employee.getName(), employee.getRole().getDisplayName());
    }
}
