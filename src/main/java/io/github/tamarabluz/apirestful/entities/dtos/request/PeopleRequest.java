package io.github.tamarabluz.apirestful.entities.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.tamarabluz.apirestful.entities.People;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeopleRequest {

    private Long id;
    @NotBlank(message = "Nome ")
    @Length(message = "Nome ")
    private String name;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate birthDate;
    private List<AddressRequest> addresses;
}
