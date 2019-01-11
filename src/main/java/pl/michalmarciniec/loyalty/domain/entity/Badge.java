package pl.michalmarciniec.loyalty.domain.entity;

import pl.michalmarciniec.loyalty.domain.command.CreateBadgeCommand;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "badges")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class Badge extends BaseEntity {

    @Column(name = "description", nullable = false, length = 300)
    String description;

    @Column(name = "image_path", nullable = false, length = 500)
    String imagePath;

    public Badge edit(CreateBadgeCommand createBadgeCommand) {
        this.description = createBadgeCommand.getDescription();
        this.imagePath = createBadgeCommand.getImagePath();
        return this;
    }
}
