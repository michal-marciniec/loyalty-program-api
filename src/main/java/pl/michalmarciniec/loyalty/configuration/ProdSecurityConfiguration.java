package pl.michalmarciniec.loyalty.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("prod")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableOAuth2Sso
public class ProdSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/", "/login**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
                .and().authorizeRequests().anyRequest().authenticated();

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
}
