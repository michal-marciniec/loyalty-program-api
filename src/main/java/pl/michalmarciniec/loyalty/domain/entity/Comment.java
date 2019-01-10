package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class Comment extends BaseEntity {

    @Column(name = "bonus_id", nullable = false)
    Long bonusId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @Column(name = "body", length = 500, nullable = false)
    String body;

    public void changeBody(String body) {
        this.body = body;
    }
}
