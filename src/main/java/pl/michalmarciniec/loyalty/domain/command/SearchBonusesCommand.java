package pl.michalmarciniec.loyalty.domain.command;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
public class SearchBonusesCommand {
    private final static String DATE_PARAM_FORMAT = "yyyy-MM-dd";

    @DateTimeFormat(pattern = DATE_PARAM_FORMAT)
    LocalDate startDate;
    @DateTimeFormat(pattern = DATE_PARAM_FORMAT)
    LocalDate endDate;
    Long memberId;
}
