package gruppe3.adventurexp.model.dto;

import gruppe3.adventurexp.model.Schedule;

import java.time.LocalDate;
import java.util.List;

public record ScheduleResponse(LocalDate date,
                               List<ReservationResponse> reservations,
                               List<AssignmentResponse> assignments) {

    public static ScheduleResponse from(final Schedule schedule) {
        return new ScheduleResponse(
                schedule.getDate(),
                schedule.getReservations().stream().map(ReservationResponse::from).toList(),
                schedule.getAssignments().stream().map(AssignmentResponse::from).toList()
        );
    }
}
