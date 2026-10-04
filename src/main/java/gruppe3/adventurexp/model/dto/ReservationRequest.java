package gruppe3.adventurexp.model.dto;

import java.time.LocalDateTime;

public record ReservationRequest(
        Long activityId,
        String name,
        String phoneNumber,
        int amountPeople,
        LocalDateTime start,
        LocalDateTime end
) {
}
