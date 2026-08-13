package dev.lumah.orders_ms.config;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("MS de E-mails para Loja Virtual")
						.description("API Rest usando Spring Java que dispara eventos integrada a um microsserviço RabbitMQ para o envio de e-mails.")
						.contact(new Contact()
								.name("Lumah Pereira")
								.url("https://github.com/lumahdev")));
	}
}