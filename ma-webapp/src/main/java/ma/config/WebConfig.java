package ma.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebConfig.java 
 * 
 * @Date: 27 sept. 2018
 */

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter
{
    /**
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("PUT", "DELETE", "POST", "GET")
            .allowCredentials(false);
    }
}