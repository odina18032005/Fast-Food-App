package uz.pdp.fast_food_app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Welcome to project",
                description = "Project on doctors.",
                version = "v1.1.1"
        ),
        servers = {
                @Server(url = "http://localhost:80",description = "For Local Mode")
        }
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "bearerAuth",
        description = "You have to add JWT Token",
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
