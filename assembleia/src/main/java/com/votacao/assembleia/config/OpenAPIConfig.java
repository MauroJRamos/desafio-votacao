package com.votacao.assembleia.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;


import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server URL em ambiente de desenvolvimento");

        Contact contact = new Contact();
        contact.setEmail("mauroramosti@gmail.com");
        contact.setName("Mauro Ramos");

        Info info = new Info()
                .title("API de Sistema de Votação")
                .version("1.0")
                .contact(contact)
                .description("API para gerenciamento de votações em assembleias. " +
                        "Esta API permite cadastrar pautas, abrir sessões de votação, " +
                        "receber votos e contabilizar resultados.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
