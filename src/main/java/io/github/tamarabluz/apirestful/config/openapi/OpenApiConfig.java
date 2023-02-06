package io.github.tamarabluz.apirestful.config.openapi;

import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST")
                        .description("API REST Customer and Address. These documents refer to the technical test for the company Attornatus.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Tamara Luz")
                                .url("https://github.com/tamarabluz")));

    }

}



