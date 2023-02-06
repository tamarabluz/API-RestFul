package io.github.tamarabluz.apirestful.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.tamarabluz.apirestful.entities.People;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PeopleRequest {

    private Long id;
    @NotBlank(message = "Nome ")
    @Length(message = "Nome ")
    private String name;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate birthDate;
}
