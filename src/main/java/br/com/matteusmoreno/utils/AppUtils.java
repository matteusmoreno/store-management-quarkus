package br.com.matteusmoreno.utils;

import br.com.matteusmoreno.client.ViaCepClient;
import br.com.matteusmoreno.client.ViaCepResponse;
import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.repository.AddressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.Period;

@ApplicationScoped
public class AppUtils {

    @Inject
    AddressRepository addressRepository;

    @Inject
    ViaCepClient viaCepClient;

    public Address setAddressAttributes(String zipcode) {
        boolean addressExists = addressRepository.existsByZipcode(zipcode);
        if (addressExists) {
            return addressRepository.findByZipcode(zipcode);
        }

        ViaCepResponse viaCepResponse = viaCepClient.getAddress(zipcode);
        return new Address(viaCepResponse);
    }

    public Integer ageCalculator(LocalDate birtDate) {
        LocalDate currentlyDate = LocalDate.now();

        return Period.between(birtDate, currentlyDate).getYears();
    }


}
