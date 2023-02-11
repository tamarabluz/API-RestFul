package io.github.tamarabluz.apirestful.entities.dtos.request;

import lombok.*;
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
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {

    private Long id;
    @NotBlank(message = "CEP")
    private String cep;
    @NotBlank(message = "Logradouro")
    private String logradouro;
    @NotNull(message = "Número")
    private Long numero;
    @NotBlank(message = "Cidade")
    private String cidade;
    private Boolean isPrincipal;

    private Long peopleId;

}
