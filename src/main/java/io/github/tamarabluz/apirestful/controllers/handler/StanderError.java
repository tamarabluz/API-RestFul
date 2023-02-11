package io.github.tamarabluz.apirestful.controllers.handler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class StanderError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;

}
