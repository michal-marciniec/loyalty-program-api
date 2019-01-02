package pl.michalmarciniec.loyalty.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectionController {

  @RequestMapping(value = "/**/{[path:[^\\.]*}")
  public String forwardNonApiCallToIndex() {
    return "forward:/";
  }

  @RequestMapping(value = "/api/{[path:[^\\.]*}")
  public ResponseEntity handleInvalidApiCall() {
    return ResponseEntity.notFound().build();
  }

}