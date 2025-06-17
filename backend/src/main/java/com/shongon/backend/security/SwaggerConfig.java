package com.shongon.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig{
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Spring Boot Oauth2 API")
                    .version("1.0.0")
                    .description("API documentation cho Spring Boot OAuth2 project")
                    .contact(new Contact()
                        .name("shongon")
                        .email("hongson@example.com")
                        )
                    )
                .components(new Components()
                    .addSecuritySchemes("oauth2", 
                        new SecurityScheme()
                            .type(SecurityScheme.Type.OAUTH2)
                            .description("Oauth2 Authentication.")
                            .flows(new OAuthFlows()
                                .authorizationCode(new OAuthFlow()
                                    .authorizationUrl("http://localhost:8080/oauth2/authorization/google")
                                    .tokenUrl("https://oauth2.googleapis.com/token")
                                )
                            )
                    )
                );
    }
}