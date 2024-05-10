package br.com.matteusmoreno.response;

import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Employee;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@JsonPropertyOrder({
        "id",
        "name",
        "birthDate",
        "age",
        "phone",
        "salary",
        "role",
        "email",
        "cpf",
        "address",
        "createdAt",
        "updatedAt",
        "deletedAt",
        "active"
})
public record EmployeeDetailsResponse(
        UUID id,
        String name,
        LocalDate birthDate,
        Integer age,
        String phone,
        BigDecimal salary,
        String role,
        String email,
        String cpf,
        Address address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public EmployeeDetailsResponse(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getBirthDate(), employee.getAge(), employee.getPhone(),
                employee.getSalary(), employee.getRole().getDisplayName(), employee.getEmail(), employee.getCpf(),
                employee.getAddress(), employee.getCreatedAt(), employee.getUpdatedAt(), employee.getDeletedAt(),
                employee.getActive());
    }
}
