package pl.michalmarciniec.loyalty.api.dto;

public class BonusDto {
    private Long giverId;
    private Long receiverId;
    private int points;

    public BonusDto() {
    }

    public BonusDto(Long giverId, Long receiverId, int points) {
        this.giverId = giverId;
        this.receiverId = receiverId;
        this.points = points;
    }

    public Long getGiverId() {
        return giverId;
    }

    public void setGiverId(Long giverId) {
        this.giverId = giverId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
