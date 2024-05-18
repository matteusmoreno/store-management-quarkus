package br.com.matteusmoreno.utils;

import br.com.matteusmoreno.client.viacep.ViaCepClient;
import br.com.matteusmoreno.client.viacep.ViaCepResponse;
import br.com.matteusmoreno.domain.Address;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
class AppUtilsTest {

    @Inject
    AppUtils appUtils;

    @InjectMock
    ViaCepClient viaCepClient;


    @Test
    public void testSetAddressAttributes() {
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
    void ageCalculator() {

        LocalDate birthDate = LocalDate.of(1990, 5, 14);

        assertEquals(34, appUtils.ageCalculator(birthDate));
    }


}