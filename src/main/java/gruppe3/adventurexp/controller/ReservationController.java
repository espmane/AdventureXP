package gruppe3.adventurexp.controller;

import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.service.ReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/company")
    public List<Reservation> reserveAllActivities(@RequestBody Reservation request) {
        return reservationService.reserveAllActivities(request);
    }
}
