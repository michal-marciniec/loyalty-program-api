package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.domain.entity.RoleName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleDto {
    Long id;
    RoleName roleName;
}
