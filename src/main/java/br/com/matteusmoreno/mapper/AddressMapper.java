package br.com.matteusmoreno.mapper;

import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.address.AddressRepository;
import br.com.matteusmoreno.client.viacep.ViaCepClient;
import br.com.matteusmoreno.client.viacep.ViaCepResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AddressMapper {

    private final ViaCepClient viaCepClient;
    private final AddressRepository addressRepository;

    @Inject
    public AddressMapper(ViaCepClient viaCepClient, AddressRepository addressRepository) {
        this.viaCepClient = viaCepClient;
        this.addressRepository = addressRepository;
    }

    public Address toEntity(String zipcode) {
        log.info("Mapping supplier to entity");
        ViaCepResponse viaCepResponse = viaCepClient.getAddress(zipcode);
        boolean addressExists = addressRepository.existsByZipcode(zipcode);

        if (addressExists) {
            return addressRepository.findByZipcode(zipcode);
        }

        return Address.builder()
                .zipcode(viaCepResponse.cep())
                .street(viaCepResponse.logradouro())
                .city(viaCepResponse.localidade())
                .neighborhood(viaCepResponse.bairro())
                .state(viaCepResponse.uf())
                .build();
    }
}
