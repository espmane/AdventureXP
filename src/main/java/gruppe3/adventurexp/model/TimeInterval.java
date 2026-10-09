package gruppe3.adventurexp.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.Duration;
import java.time.LocalDateTime;

/** TimeInterval represents a period of time with a start time and an end time.*/
@Embeddable
public record TimeInterval(LocalDateTime start, LocalDateTime end) {

    public TimeInterval {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end must not be null");
        }
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start needs to be before end");
        }
    }

    /** Checks if this TimeInterval overlaps with the other TimeInterval*/
    public boolean overlaps(final TimeInterval interval) {
        return start.isBefore(interval.end) && end.isAfter(interval.start);
    }

    public int getDurationInHours() {
        return (int) Duration.between(start, end).toHours();
    }
}
