package br.com.matteusmoreno.domain;

import br.com.matteusmoreno.client.ViaCepResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zipcode;
    private String street;
    private String city;
    private String neighborhood;
    private String state;

    public Address(ViaCepResponse viaCepResponse) {
        this.zipcode = viaCepResponse.cep();
        this.street = viaCepResponse.logradouro();
        this.city = viaCepResponse.localidade();
        this.neighborhood = viaCepResponse.bairro();
        this.state = viaCepResponse.uf();
    }
}
