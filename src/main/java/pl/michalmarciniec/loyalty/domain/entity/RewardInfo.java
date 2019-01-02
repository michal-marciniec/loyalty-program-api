package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class RewardInfo {

    @Column(name = "price", nullable = false)
    Long price;

    @Column(name = "description", nullable = false, length = 300)
    String description;

    @Column(name = "logo_path", nullable = false, length = 500)
    String logoPath;
}
