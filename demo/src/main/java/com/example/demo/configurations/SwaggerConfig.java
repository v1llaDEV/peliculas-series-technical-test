package com.example.demo.configurations;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {


    @Value("${project.openapi.server-url}")
    private String serverUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription("Servidor URL local");

        Contact contact = new Contact();
        contact.setEmail("adrianvillasecosoto@gmail.com");
        contact.setName("v1llaDEV");
        contact.setUrl("https://github.com/v1llaDEV");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("API para la gestión de peliculas, series y empleados")
                .version("1.0")
                .contact(contact)
                .description("Este API expone servicios para manegar películas, series y los empleados de la compañia.")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(server));
    }



}
