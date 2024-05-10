package br.com.matteusmoreno.client;

public record ViaCepResponse(
        String cep,
        String logradouro,
        String localidade,
        String bairro,
        String uf) {
}