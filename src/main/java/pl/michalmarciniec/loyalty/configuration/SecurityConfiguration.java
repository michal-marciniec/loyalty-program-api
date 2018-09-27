package pl.michalmarciniec.loyalty.configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class SecurityConfiguration extends WebSecurityConfigurerAdapter {
}
