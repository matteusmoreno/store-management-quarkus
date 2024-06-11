package br.com.matteusmoreno.utils;

import br.com.matteusmoreno.customer.CustomerRepository;
import br.com.matteusmoreno.exception.exception_class.DuplicateEntryException;
import br.com.matteusmoreno.product.Product;
import br.com.matteusmoreno.product.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Validation {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Inject
    public Validation(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    // VALIDA SE ALGUM CAMPO JÁ ESTÁ CADASTRADO NO BANCO DE DADOS
    public void validateEntryDuplicity(String email, String cpf, String phone) {

        boolean emailExists = customerRepository.existsByEmail(email);
        boolean cpfExists = customerRepository.existsByCpf(cpf);
        boolean phoneExists = customerRepository.existsByPhone(phone);

        if (emailExists) {
            throw new DuplicateEntryException("Email already exists.");
        }
        if (cpfExists) {
            throw new DuplicateEntryException("CPF already exists.");
        }
        if (phoneExists) {
            throw new DuplicateEntryException("Phone number already exists.");
        }
    }

    // VALIDA SE O EMAIL JÁ ESTÁ CADASTRADO NO BANCO DE DADOS
    public void validateEmailDuplicity(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateEntryException("Email already exists.");
        }
    }

    // VALIDA SE O CPF JÁ ESTÁ CADASTRADO NO BANCO DE DADOS
    public void validateCpfDuplicity(String cpf) {
        if (customerRepository.existsByCpf(cpf)) {
            throw new DuplicateEntryException("CPF already exists.");
        }
    }

    // VALIDA SE O telefone JÁ ESTÁ CADASTRADO NO BANCO DE DADOS
    public void validatePhoneDuplicity(String phone) {
        if (customerRepository.existsByPhone(phone)) {
            throw new DuplicateEntryException("Phone number already exists.");
        }
    }
}
