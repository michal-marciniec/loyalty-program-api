package pl.michalmarciniec.loyalty.api;

import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/hello")
@LoyaltyProgramApi
public class HelloEndpoint {

    @RequestMapping(method = GET)
    public String sayHello() {
        return "Hello!";
    }

}

