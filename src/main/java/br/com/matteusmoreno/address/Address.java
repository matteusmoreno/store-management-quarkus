package br.com.matteusmoreno.address;

import br.com.matteusmoreno.client.viacep.ViaCepResponse;
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

    public Address(ViaCepResponse response) {
        this.zipcode = response.cep();
        this.street = response.logradouro();
        this.city = response.localidade();
        this.neighborhood = response.bairro();
        this.state = response.uf();
    }
}
