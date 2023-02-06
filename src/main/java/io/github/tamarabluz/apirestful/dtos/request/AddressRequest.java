package io.github.tamarabluz.apirestful.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    private Long id;
    @NotBlank(message = "Logradouro")
    private String logradouro;
    @NotBlank(message = "CEP")
    private String cep;
    @NotNull(message = "Número")
    private int numero;
    @NotBlank(message = "Cidade")
    private String cidade;
}
