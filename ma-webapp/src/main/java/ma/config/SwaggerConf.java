package ma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//Swagger 2 est activé via l' annotation @ EnableSwagger2 .
@Configuration
@EnableSwagger2
public class SwaggerConf {
	// Une fois le bean Docket défini, sa méthode select () renvoie une instance
	// de ApiSelectorBuilder , qui permet de contrôler les points de terminaison
	// exposés par Swagger.
	@Bean
	public Docket api() {
		/**
		 * Les prédicats de sélection de RequestHandler peuvent être configurés
		 * à l’aide de RequestHandlerSelectors et PathSelectors . En utilisant
		 * any () pour les deux, la documentation de l'intégralité de votre API
		 * sera disponible via Swagger.
		 * 
		 * Cette configuration est suffisante pour intégrer Swagger 2 au projet
		 * Spring Boot existant. Pour les autres projets Spring, des ajustements
		 * supplémentaires sont nécessaires.
		 */
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

}
