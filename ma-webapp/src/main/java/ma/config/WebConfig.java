package ma.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig.java
 * 
 * @Date: 27 sept. 2018
 */

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	/**
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "DELETE", "POST", "GET")
				.allowCredentials(false);
	}

	/**
	 * Swagger UI ajoute un ensemble de ressources que vous devez configurer
	 * dans le cadre d'une classe qui étend WebMvcConfigurerAdapter et est
	 * annoté avec @EnableWebMvc.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}