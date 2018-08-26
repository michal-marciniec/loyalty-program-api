package pl.michalmarciniec.loyalty.api;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RestController
@Retention(RetentionPolicy.RUNTIME)
public @interface LoyaltyProgramApi {
}
