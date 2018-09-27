package pl.michalmarciniec.loyalty.domain;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Builder
@Value
public class User {
    Long id;
    String name;
    String avatarPath;
    String email;
    Set<String> roles;
}
