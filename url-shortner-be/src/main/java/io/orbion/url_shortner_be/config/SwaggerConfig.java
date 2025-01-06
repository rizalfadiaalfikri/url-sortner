package io.orbion.url_shortner_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer",
                        new SecurityScheme().type(Type.HTTP).scheme("Bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Url Shortner")
                        .description("Url Shortner API")
                        .version("1.0.0"));
    }

}
