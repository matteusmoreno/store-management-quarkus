package br.com.matteusmoreno.customer.customer_response;

import br.com.matteusmoreno.customer.Customer;

public record ResumeCustomerResponse(
        String name,
        String phone,
        String email) {

    public ResumeCustomerResponse(Customer customer) {
        this(customer.getName(), customer.getPhone(), customer.getEmail());
    }
}
