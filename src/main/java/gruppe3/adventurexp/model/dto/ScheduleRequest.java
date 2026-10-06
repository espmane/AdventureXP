package gruppe3.adventurexp.model.dto;

import java.time.LocalDate;
import java.util.List;

public record ScheduleRequest(LocalDate date, List<ReservationRequest> reservations) {

}
