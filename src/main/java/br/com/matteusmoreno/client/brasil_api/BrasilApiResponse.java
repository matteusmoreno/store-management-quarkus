package br.com.matteusmoreno.client.brasil_api;

public record BrasilApiResponse(
        String razao_social,
        String nome_fantasia,
        String cep,
        String ddd_telefone_1,
        String descricao_situacao_cadastral,
        String natureza_juridica,
        String email) {
}
