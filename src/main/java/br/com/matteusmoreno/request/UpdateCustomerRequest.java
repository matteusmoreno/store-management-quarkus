package br.com.matteusmoreno.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCustomerRequest(
        @NotNull(message = "Coloca a porra do id")
        Long id,
        String name) {
}
