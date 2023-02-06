package io.github.tamarabluz.apirestful.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
