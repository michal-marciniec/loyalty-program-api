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
    LocalDateTime startDate;
    @DateTimeFormat(pattern = DATE_PARAM_FORMAT)
    LocalDateTime endDate;
    Long memberId;

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate.atStartOfDay();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate.atTime(LocalTime.MAX);
    }
}
