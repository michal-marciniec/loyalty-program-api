package pl.michalmarciniec.loyalty;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/hello")
public class HelloEndpoint {

    @RequestMapping(method = GET)
    public String sayHello() {
        return "Hello!";
    }

}
