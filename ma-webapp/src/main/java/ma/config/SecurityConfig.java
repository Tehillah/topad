package ma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SecurityConfig.java 
 * 
 * @Date: 27 sept. 2018
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     ** @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)

    {
        auth
            .eraseCredentials(true);

    }

    /**
     * @param http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/restapi/**", "/soapws/**").permitAll()
            .anyRequest().authenticated();
    }

    /**
     ** @param
     **/
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}