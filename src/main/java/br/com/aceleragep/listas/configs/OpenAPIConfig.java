package br.com.aceleragep.listas.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI springShopOpenAPI() {
		OpenAPI openAPI = new OpenAPI();
		//Aqui é onde setamos título, versão e info da OpenAPI do projeto.
		Info info = new Info(); 
		info.title("Listas"); 
		info.version("v0.0.1"); 
		openAPI.info(info); 
		info.description("<h2>Teste 03 do AceleraG&P</h2>");
		
		openAPI.addTagsItem(new Tag().name("Lista").description("Gerencia as listas do sistema"));
		openAPI.addTagsItem(new Tag().name("Item").description("Gerencia os itens do sistema"));
		
		return openAPI;
	}
}
