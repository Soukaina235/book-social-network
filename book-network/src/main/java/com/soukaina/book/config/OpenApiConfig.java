package com.soukaina.book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info( // this isn't mandatory
                contact = @Contact(
                        name = "Soukaina",
                        email = "soukaina.jabri001@gmail.com",
                        url = "https://soukaina-website.com"
                ),
                description = "OpenAPi documentation for Spring Security",
                title = "OpenApi specification", // will be used in the frontend
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8088/api/v1" // the base URL
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://some-url.com"
                )
        },
        security = {
                // for each controller in our API, we need a security requirement
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth", // the name should be the exact same one as the one in the @SecurityRequirement
        description = "JWT auth description",
        scheme = "bearer", // the type of the scheme is a bearer token
        type = SecuritySchemeType.HTTP, // it is a bearer token for HTTP
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER // where we want to include this bearer token
)
public class OpenApiConfig {
}
