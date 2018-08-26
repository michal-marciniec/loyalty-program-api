package pl.michalmarciniec.loyalty.db;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bonuses")
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "points", nullable = false)
    private int points;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "giver_id")
    private Member giver;

    @Column(name = "given_at", nullable = false)
    private LocalDateTime givenAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getGiver() {
        return giver;
    }

    public void setGiver(Member giver) {
        this.giver = giver;
    }

    public LocalDateTime getGivenAt() {
        return givenAt;
    }

    public void setGivenAt(LocalDateTime givenAt) {
        this.givenAt = givenAt;
    }
}
