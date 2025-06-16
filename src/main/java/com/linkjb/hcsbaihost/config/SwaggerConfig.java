package com.linkjb.hcsbaihost.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerOpenApi() {
        return new OpenAPI()
                .info(new Info().title("shark-mcpHosts")
                        .description("mcp探索")
                        .version("v1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("我的github")
                        .url("https://github.com/linkshark"));
    }
}
