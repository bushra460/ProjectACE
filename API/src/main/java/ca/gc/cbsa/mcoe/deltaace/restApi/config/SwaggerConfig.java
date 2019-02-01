package ca.gc.cbsa.mcoe.deltaace.restApi.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("ca.gc.cbsa.mcoe.deltaace.restApi.controller"))
				.paths(paths())
				.build()
				.apiInfo(apiInfo());
	}

	private Predicate<String> paths() {
		return Predicates.not(PathSelectors.regex("/error.*"));
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Project Delta ACE API", 
	      "API for the Project Delta ACE micro-mission.", 
	      "Version 1", 
	      "", 
	      new Contact("","",""), 
	      "", "", Collections.emptyList());
	}
}
