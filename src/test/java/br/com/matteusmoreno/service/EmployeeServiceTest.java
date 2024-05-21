package br.com.matteusmoreno.service;

import br.com.matteusmoreno.constant.Role;
import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Customer;
import br.com.matteusmoreno.domain.Employee;
import br.com.matteusmoreno.repository.EmployeeRepository;
import br.com.matteusmoreno.request.CreateEmployeeRequest;
import br.com.matteusmoreno.request.UpdateEmployeeRequest;
import br.com.matteusmoreno.utils.AppUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    AppUtils appUtils;

    @InjectMocks
    EmployeeService employeeService;

    private UUID uuid;
    private Address address;
    private Employee employee;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        uuid = UUID.randomUUID();
        address = new Address(1L, "28994-666", "St. A", "City", "Neighborhood", "RH");
        employee = new Employee(uuid, "Name", LocalDate.of(1990,8,28), 33, "(99)999999999", new BigDecimal("10000"), Role.MECHANIC,
                "email@email.com", "888.888.888-88", address, LocalDateTime.now(), null, null, true);
    }

    @Test
    @DisplayName("Should create a employee and save it to the repository")
    void shouldCreateEmployeeAndSaveToRepository() {

        CreateEmployeeRequest request = new CreateEmployeeRequest("Name", LocalDate.of(1990,8,28),
                "(99)999999999", new BigDecimal("10000"), Role.MECHANIC, "email@email.com", "888.888.888-88", "28994-666");

        when(appUtils.setAddressAttributes(request.zipcode())).thenReturn(address);
        when(appUtils.ageCalculator(request.birthDate())).thenReturn(33);

        Employee result = employeeService.createEmployee(request);
        result.setId(uuid);


        verify(appUtils, times(1)).setAddressAttributes(request.zipcode());
        verify(appUtils, times(1)).ageCalculator(request.birthDate());
        verify(employeeRepository, times(1)).save(result);

        assertEquals(uuid, result.getId());
        assertEquals(request.name(), result.getName());
        assertEquals(request.birthDate(), result.getBirthDate());
        assertEquals(33, result.getAge());
        assertEquals(request.phone(), result.getPhone());
        assertEquals(request.salary(), result.getSalary());
        assertEquals(request.role(), result.getRole());
        assertEquals(request.email(), result.getEmail());
        assertEquals(request.cpf(), result.getCpf());
        assertEquals(address, result.getAddress());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("Should return employee details by ID")
    void shouldReturnEmployeeDetailsById() {

        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));

        Optional<Employee> result = Optional.ofNullable(employeeService.employeeDetails(uuid));

        verify(employeeRepository, times(1)).findById(uuid);

        assertTrue(result.isPresent());
        assertEquals(employee, result.get());
    }

    @Test
    @DisplayName("Should update employee and save it to the repository")
    void shouldUpdateEmployeeAndSaveToRepository() {

        UpdateEmployeeRequest request = new UpdateEmployeeRequest(uuid, "New Name", LocalDate.of(1989,8,28),
                "(66)666666666", new BigDecimal("2500.00"), Role.ACCOUNTANT, "newemail@email.com", "000.000.000-00", "28994-666");
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");

        when(employeeRepository.findById(request.id())).thenReturn(Optional.of(employee));
        when(appUtils.setAddressAttributes(request.zipcode())).thenReturn(newAddress);
        when(appUtils.ageCalculator(request.birthDate())).thenReturn(34);

        Employee result = employeeService.updateEmployee(request);

        verify(employeeRepository, times(1)).findById(request.id());
        verify(employeeRepository, times(1)).save(result);
        verify(appUtils, times(1)).setAddressAttributes(request.zipcode());
        verify(appUtils, times(1)).ageCalculator(request.birthDate());

        assertEquals(request.name(), result.getName());
        assertEquals(request.birthDate(), result.getBirthDate());
        assertEquals(34, result.getAge());
        assertEquals(request.phone(), result.getPhone());
        assertEquals(request.salary(), result.getSalary());
        assertEquals(request.role(), result.getRole());
        assertEquals(request.email(), result.getEmail());
        assertEquals(request.cpf(), result.getCpf());
        assertEquals(newAddress, result.getAddress());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("Should disable a employee and mark them as deleted")
    void shouldDisableEmployeeAndMarkAsDeleted() {

        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));

        employeeService.disableEmployee(uuid);

        verify(employeeRepository, times(1)).findById(uuid);
        verify(employeeRepository, times(1)).save(employee);
        assertFalse(employee.getActive());
        assertNotNull(employee.getDeletedAt());
    }

    @Test
    @DisplayName("Should enable a employee and mark them as updated")
    void shouldEnableEmployeeAndMarkAsUpdated() {

        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));

        Employee result = employeeService.enableCustomer(uuid);

        verify(employeeRepository, times(1)).findById(uuid);
        verify(employeeRepository, times(1)).save(result);

        assertTrue(employee.getActive());
        assertNull(employee.getDeletedAt());
        assertNotNull(employee.getUpdatedAt());
    }
}