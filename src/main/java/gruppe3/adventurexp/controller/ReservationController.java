package gruppe3.adventurexp.controller;

import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.model.dto.ReservationRequest;
import gruppe3.adventurexp.model.dto.ReservationResponse;
import gruppe3.adventurexp.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {

        final var response =
                toResponse(reservationService.getAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(
            @PathVariable final int id) {

        final var response =
                ReservationResponse.from(reservationService.getById(id));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ReservationResponse> saveReservation(
            @RequestBody final ReservationRequest request) {

        final var response =
                ReservationResponse.from(reservationService.save(request));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ReservationResponse> updateReservation(
            @RequestBody final ReservationRequest request) {

        final var response =
                ReservationResponse.from(reservationService.update(request));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable final int id) {

        reservationService.remove(id);

        return ResponseEntity.noContent().build();
    }

    private List<ReservationResponse> toResponse(
            final List<Reservation> reservations) {

        final List<ReservationResponse> responses = new ArrayList<>();

        for (final Reservation reservation : reservations) {
            responses.add(ReservationResponse.from(reservation));
        }

        return responses;
    }
}