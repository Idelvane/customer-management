package io.github.idelvane.customermanagement.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe com as configurações ncessárias para utilizar o Swager na documentação da API
 *  
 * @author Idelvane
 */
@Configuration
@Profile({"dev"})
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Value("${api.version}")
	private String apiVersion;
	
	/**
	 * Configuração dos endpoints
	 *  
	 * @return <code>Docket</code> object
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("io.github.idelvane.customermanagement.controller"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	/**
	 * Informações da API 
	 * 
	 * @return <code>ApiInfo</code> object
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Customer management")
				.description("API do projeto Customer Management").version(apiVersion)
				.build();
	}

}
