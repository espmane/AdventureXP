package gruppe3.adventurexp.model.dto;

import gruppe3.adventurexp.model.Reservation;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        Long activityId,
        String name,
        String phoneNumber,
        int amountPeople,
        LocalDateTime start,
        LocalDateTime end,
        int price
) {

    public static ReservationResponse from(final Reservation reservation) {
        final var timeInterval = reservation.getTimeInterval();
        return new ReservationResponse(
                reservation.getId(),
                reservation.getActivity().getId(),
                reservation.getName(),
                reservation.getPhoneNumber(),
                reservation.getAmountPeople(),
                timeInterval.start(),
                timeInterval.end(),
                reservation.getPrice()
        );
    }
}
