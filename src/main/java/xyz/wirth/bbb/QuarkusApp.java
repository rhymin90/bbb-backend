package xyz.wirth.bbb;

import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
    info = @Info(title = "Bachelor Bullshit Bingo API", version = "1.1.0"),
    security = @SecurityRequirement(name = "bearerAuth"),
    components =
        @Components(
            securitySchemes = {
              @SecurityScheme(
                  securitySchemeName = "bearerAuth",
                  type = SecuritySchemeType.HTTP,
                  scheme = "bearer",
                  bearerFormat = "JWT"),
            }))
@LoginConfig(authMethod = "MP-JWT")
@Path("/")
public class QuarkusApp extends Application {}
