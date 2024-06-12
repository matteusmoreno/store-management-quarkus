package br.com.matteusmoreno.service;

import br.com.matteusmoreno.employee.constant.EmployeeRole;
import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.employee.Employee;
import br.com.matteusmoreno.employee.EmployeeService;
import br.com.matteusmoreno.employee.EmployeeRepository;
import br.com.matteusmoreno.employee.employee_request.CreateEmployeeRequest;
import br.com.matteusmoreno.employee.employee_request.UpdateEmployeeRequest;
import br.com.matteusmoreno.utils.AppUtils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
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
    private Validator validator;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        uuid = UUID.randomUUID();
        address = new Address(1L, "28994-666", "St. A", "City", "Neighborhood", "RH");
        employee = new Employee(uuid, "Name", LocalDate.of(1990,8,28), 33, "(99)999999999", new BigDecimal("10000"), EmployeeRole.MECHANIC,
                "email@email.com", "888.888.888-88", address, LocalDateTime.now(), null, null, true);
    }

    @Test
    @DisplayName("Should create a employee and save it to the repository")
    void shouldCreateEmployeeAndSaveToRepository() {

        CreateEmployeeRequest request = new CreateEmployeeRequest("Name", LocalDate.of(1990,8,28),
                "(99)999999999", new BigDecimal("10000"), EmployeeRole.MECHANIC, "email@email.com", "888.888.888-88", "28994-666");

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
    @DisplayName("Should throw ConstraintViolationException when creating an employee with invalid fields")
    void shouldThrowConstraintViolationExceptionWhenCreatingEmployeeWithInvalidFields() {
        // Teste para nome em branco
        CreateEmployeeRequest requestWithBlankName = new CreateEmployeeRequest("", LocalDate.of(1990, 8, 28),
                "(22)998223307", new BigDecimal("1500.00"), EmployeeRole.MECHANIC, "email@email.com", "222.222.222-22", "22222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithBlankName).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });

        // Teste para email inválido
        CreateEmployeeRequest requestWithInvalidEmail = new CreateEmployeeRequest("Name", LocalDate.of(1990, 8, 28),
                "(22)998223307", new BigDecimal("1500.00"), EmployeeRole.MECHANIC, "invalid-email", "222.222.222-22", "22222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithInvalidEmail).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });

        // Teste para telefone inválido
        CreateEmployeeRequest requestWithInvalidPhone = new CreateEmployeeRequest("Name", LocalDate.of(1990, 8, 28),
                "998223307", new BigDecimal("1500.00"), EmployeeRole.MECHANIC, "email@email.com", "222.222.222-22", "22222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithInvalidPhone).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });

        // Teste para CPF inválido
        CreateEmployeeRequest requestWithInvalidCpf = new CreateEmployeeRequest("Name", LocalDate.of(1990, 8, 28),
                "(22)998223307", new BigDecimal("1500.00"), EmployeeRole.MECHANIC, "email@email.com", "222-22-22", "22222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithInvalidCpf).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });

        // Teste para CEP inválido
        CreateEmployeeRequest requestWithInvalidCep = new CreateEmployeeRequest("Name", LocalDate.of(1990, 8, 28),
                "(22)998223307", new BigDecimal("1500.00"), EmployeeRole.MECHANIC, "email@email.com", "222.222.222-22", "2222-666");

        assertThrows(ConstraintViolationException.class, () -> {
            validator.validate(requestWithInvalidCep).forEach(violation -> {
                throw new ConstraintViolationException(violation.getMessage(), Set.of(violation));
            });
        });
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
    @DisplayName("Should throw NoSuchElementException when employee ID not found")
    void shouldThrowNoSuchElementExceptionWhenEmployeeIdNotFound() {
        when(employeeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            employeeService.employeeDetails(uuid);
        });

        verify(employeeRepository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("Should update employee and save it to the repository")
    void shouldUpdateEmployeeAndSaveToRepository() {

        UpdateEmployeeRequest request = new UpdateEmployeeRequest(uuid, "New Name", LocalDate.of(1989,8,28),
                "(66)666666666", new BigDecimal("2500.00"), EmployeeRole.ACCOUNTANT, "newemail@email.com", "000.000.000-00", "28994-666");
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");

        when(employeeRepository.findById(request.id())).thenReturn(Optional.of(employee));
        when(appUtils.setAddressAttributes(request.zipcode())).thenReturn(newAddress);
        when(appUtils.ageCalculator(request.birthDate())).thenReturn(34);

        Employee result = employeeService.updateEmployee(request);

        verify(employeeRepository, times(1)).findById(request.id());
        verify(employeeRepository, times(1)).save(result);
        verify(appUtils, times(1)).setAddressAttributes(request.zipcode());
        verify(appUtils, times(1)).ageCalculator(request.birthDate());

        assertEquals(request.id(), result.getId());
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
    @DisplayName("Should throw NoSuchElementException when updating a employee that does not exist")
    void shouldThrowNoSuchElementExceptionWhenUpdatingNonExistentEmployee() {
        Address newAddress = new Address(2L, "28994-675", "St. B", "City B", "Neighborhood B", "RA");
        UpdateEmployeeRequest request = new UpdateEmployeeRequest(employee.getId(), "New Name", LocalDate.of(1989, 8, 28), "newemail@email.com",
                new BigDecimal("2000"), EmployeeRole.MECHANIC, "newemail@email.com", "154.895.698-98", newAddress.getZipcode());

        when(employeeRepository.findById(request.id())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            employeeService.updateEmployee(request);
        });

        verify(employeeRepository, times(1)).findById(request.id());
        verify(employeeRepository, times(0)).save(any(Employee.class));
        verify(appUtils, times(0)).ageCalculator(any(LocalDate.class));
        verify(appUtils, times(0)).setAddressAttributes(anyString());
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
    @DisplayName("Should throw NoSuchElementException when disabling a employee that does not exist")
    void shouldThrowNoSuchElementExceptionWhenDisablingNonExistentEmployee() {
        when(employeeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            employeeService.disableEmployee(uuid);
        });

        verify(employeeRepository, times(1)).findById(uuid);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should enable a employee and mark them as updated")
    void shouldEnableEmployeeAndMarkAsUpdated() {

        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));

        Employee result = employeeService.enableCustomer(uuid);

        verify(employeeRepository, times(1)).findById(uuid);
        verify(employeeRepository, times(1)).save(result);

        assertTrue(result.getActive());
        assertNull(result.getDeletedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when enabling a employee that does not exist")
    void shouldThrowNoSuchElementExceptionWhenEnablingNonExistentEmployee() {
        when(employeeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            employeeService.enableCustomer(uuid);
        });

        verify(employeeRepository, times(1)).findById(uuid);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }
}