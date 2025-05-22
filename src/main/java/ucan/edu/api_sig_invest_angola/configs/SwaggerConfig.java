package ucan.edu.api_sig_invest_angola.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

    // 🔐 Configuração global do Swagger com JWT
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SIG Invest Angola API")
                        .version("1.0")
                        .description("API com autenticação JWT"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    // 🔹 Agrupamento de endpoints de autenticação
    @Bean
    public GroupedOpenApi authGroup() {
        return GroupedOpenApi.builder()
                .group("Autenticação")
                .pathsToMatch("/auth/**")
                .build();
    }

    // 🔹 Agrupamento de endpoints de empreendedor
    @Bean
    public GroupedOpenApi empreendedorGroup() {
        return GroupedOpenApi.builder()
                .group("Empreendedor")
                .pathsToMatch("/empreendedor/**")
                .build();
    }

    // 🔹 Agrupamento de endpoints de investidor
    @Bean
    public GroupedOpenApi investidorGroup() {
        return GroupedOpenApi.builder()
                .group("Investidores")
                .pathsToMatch("/investidor/**")
                .build();
    }

    @Bean
    public GroupedOpenApi SuporteGroup() {
        return GroupedOpenApi.builder()
                .group("Suporte Negocio")
                .pathsToMatch("/suporte-negocio/**")
                .build();
    }
    @Bean
    public GroupedOpenApi EnderecoGroup() {
        return GroupedOpenApi.builder()
                .group("Endereco")
                .pathsToMatch("/endereco/**")
                .build();
    }
}
