package com.es.banco.app.banco_hcb.configs;

import org.springframework.context.annotation.*;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API del Sistema Bancario",
        version = "1.0",
        description = "Documentación automática generada con Springdoc OpenAPI 3",
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    )
)
public class OpenAPIConfig {
    
}
