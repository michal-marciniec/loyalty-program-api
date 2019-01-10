package pl.michalmarciniec.loyalty.domain.entity;

import com.google.common.base.Preconditions;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedEntityGraph(name = "bonusWithComments", attributeNodes = @NamedAttributeNode("comments"))
@Table(name = "bonuses")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class Bonus extends BaseEntity {

    @Column(name = "points", nullable = false)
    Long points;

    @Column(name = "giver_id", nullable = false)
    Long giverId;

    @Column(name = "receiver_id", nullable = false)
    Long receiverId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    BonusCategory category;

    @Column(name = "description", length = 300, nullable = false)
    String description;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "bonus_id")
    List<Comment> comments = new ArrayList<>();

    public void changeDescriptionAndPoints(String description, Long points) {
        Preconditions.checkNotNull(description, points);
        this.description = description;
        this.points = points;
    }

    public void addComment(Comment comment) {
        Preconditions.checkNotNull(comment);
        comments.add(comment);
    }
}
