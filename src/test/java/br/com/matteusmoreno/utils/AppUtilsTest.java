package br.com.matteusmoreno.utils;

import br.com.matteusmoreno.client.brasil_api.BrasilApiClient;
import br.com.matteusmoreno.client.brasil_api.BrasilApiResponse;
import br.com.matteusmoreno.client.viacep.ViaCepClient;
import br.com.matteusmoreno.client.viacep.ViaCepResponse;
import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Supplier;
import br.com.matteusmoreno.exception.InvalidCepException;
import br.com.matteusmoreno.exception.InvalidCnpjException;
import br.com.matteusmoreno.repository.AddressRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class AppUtilsTest {

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private BrasilApiClient brasilApiClient;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AppUtils appUtils;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should set address attributes correctly")
    void shouldSetAddressAttributesCorrectly() {
        ViaCepResponse mockResponse = new ViaCepResponse("28994-675", "Rua Alfredo Menezes", "Saquarema",
                "Bacaxá (Bacaxá)", "RJ");

        when(viaCepClient.getAddress("28994-675")).thenReturn(mockResponse);

        Address address = appUtils.setAddressAttributes("28994-675");

        assertEquals("28994-675", address.getZipcode());
        assertEquals("Rua Alfredo Menezes", address.getStreet());
        assertEquals("Saquarema", address.getCity());
        assertEquals("Bacaxá (Bacaxá)", address.getNeighborhood());
        assertEquals("RJ", address.getState());
    }

    @Test
    @DisplayName("Should throw InvalidCepException when zipcode is invalid")
    void shouldThrowInvalidCepExceptionWhenZipcodeIsInvalid() {
        String invalidZipcode = "12345678"; // Exemplo de um CEP inválido
        when(addressRepository.existsByZipcode(invalidZipcode)).thenReturn(false);
        when(viaCepClient.getAddress(invalidZipcode)).thenReturn(new ViaCepResponse(null, null,
                null ,null, null));

        assertThrows(InvalidCepException.class, () -> {
            appUtils.setAddressAttributes(invalidZipcode);
        });

        verify(addressRepository, never()).findByZipcode(anyString());
    }

    @Test
    @DisplayName("Should Throw InvalidCnpjException When Api Returns 400")
    void shouldThrowInvalidCnpjExceptionWhenApiReturns400() {
        String invalidCnpj = "12345678000195";
        when(brasilApiClient.getSupplier(invalidCnpj)).thenThrow(new InvalidCnpjException("Invalid CNPJ"));

        assertThrows(InvalidCnpjException.class, () -> {
            appUtils.setSupplierAttributes(invalidCnpj);
        });
    }

}