package pl.michalmarciniec.loyalty.domain.command;

import lombok.Value;

@Value
public class GiveBonusCommand {
    Long giverId;
    Long receiverId;
    int points;
}
