package br.com.matteusmoreno.client.viacep;

public record ViaCepResponse(
        String cep,
        String logradouro,
        String localidade,
        String bairro,
        String uf) {
}