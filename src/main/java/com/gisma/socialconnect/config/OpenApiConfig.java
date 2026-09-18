package com.gisma.socialconnect.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI socialConnectOpenApi() {
        return new OpenAPI().info(new Info()
                .title("SocialConnect API")
                .description("Backend API for SocialConnect - M604 Advanced Programming individual project (Gisma University).")
                .version("1.0.0")
                .contact(new Contact().name("Aarsh").email("aarshforstud@gmail.com")));
    }
}
