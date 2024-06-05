package br.com.matteusmoreno.utils;

import br.com.matteusmoreno.client.brasil_api.BrasilApiClient;
import br.com.matteusmoreno.client.brasil_api.BrasilApiResponse;
import br.com.matteusmoreno.client.viacep.ViaCepClient;
import br.com.matteusmoreno.client.viacep.ViaCepResponse;
import br.com.matteusmoreno.domain.Address;
import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.domain.Supplier;
import br.com.matteusmoreno.exception.InvalidCepException;
import br.com.matteusmoreno.exception.InvalidCnpjException;
import br.com.matteusmoreno.repository.AddressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@ApplicationScoped
public class AppUtils {

    private final AddressRepository addressRepository;
    private final ViaCepClient viaCepClient;
    private final BrasilApiClient brasilApiClient;

    @Inject
    public AppUtils(AddressRepository addressRepository, ViaCepClient viaCepClient, BrasilApiClient brasilApiClient) {
        this.addressRepository = addressRepository;
        this.viaCepClient = viaCepClient;
        this.brasilApiClient = brasilApiClient;
    }

    public Address setAddressAttributes(String zipcode) {
        boolean addressExists = addressRepository.existsByZipcode(zipcode);
        if (addressExists) {
            return addressRepository.findByZipcode(zipcode);
        }

        ViaCepResponse viaCepResponse = viaCepClient.getAddress(zipcode);

        if (viaCepResponse.cep() == null) {
            throw new InvalidCepException("Invalid CEP");
        }

        return new Address(viaCepResponse);
    }

    public Supplier setSupplierAttributes(String cnpj) {
        String formattedCnpj = cnpj.replace("-", "").replace("/", "");
        BrasilApiResponse brasilApiResponse = brasilApiClient.getSupplier(formattedCnpj);
        Address supplierAddress = setAddressAttributes(brasilApiResponse.cep());

        Supplier supplier = new Supplier(brasilApiResponse);
        supplier.setCnpj(cnpj);
        supplier.setAddress(supplierAddress);

        return supplier;
    }

    public Integer ageCalculator(LocalDate birtDate) {
        LocalDate currentlyDate = LocalDate.now();

        return Period.between(birtDate, currentlyDate).getYears();
    }

    public BigDecimal costCalculator(List<Product> products, BigDecimal laborPrice) {
        BigDecimal productsPrice = products.stream()
                .map(Product::getSalePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return productsPrice.add(laborPrice);
    }



}
