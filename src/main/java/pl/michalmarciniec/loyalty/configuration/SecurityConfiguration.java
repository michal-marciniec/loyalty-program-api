package pl.michalmarciniec.loyalty.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public abstract class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public WebMvcConfigurer corsConfigurer(@Value("${security.allowedOrigins}") String allowedOrigins) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                    .addMapping("/**")
                    .allowedOrigins(allowedOrigins.split(","))
                    .allowedHeaders("*")
                    .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
            }
        };
    }

}
