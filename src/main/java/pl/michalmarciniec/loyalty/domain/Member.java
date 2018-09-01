package pl.michalmarciniec.loyalty.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar_path", nullable = false)
    private String avatarPath;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private List<Bonus> bonuses;

    public Bonus receiveBonus(Long giverId, int points) {
        Bonus bonus = new Bonus();
        bonus.setPoints(points);
        bonus.setGivenAt(LocalDateTime.now());
        bonus.setGiverId(giverId);
        bonuses.add(bonus);
        return bonus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<Bonus> bonuses) {
        this.bonuses = bonuses;
    }
}
