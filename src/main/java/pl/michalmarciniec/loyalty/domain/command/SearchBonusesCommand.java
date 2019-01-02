package pl.michalmarciniec.loyalty.domain.command;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SearchBonusesCommand {
    private final static String DATE_PARAM_FORMAT = "yyyy-MM-dd";

    @DateTimeFormat(pattern = DATE_PARAM_FORMAT)
    LocalDateTime givenFrom;
    @DateTimeFormat(pattern = DATE_PARAM_FORMAT)
    LocalDateTime givenTo;
    Long giverId;
    Long receiverId;

    public void setGivenFrom(LocalDate givenFrom) {
        this.givenFrom = givenFrom.atStartOfDay();
    }

    public void setGivenTo(LocalDate givenTo) {
        this.givenTo = givenTo.atTime(LocalTime.MAX);
    }
}
