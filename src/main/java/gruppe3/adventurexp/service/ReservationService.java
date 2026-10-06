package gruppe3.adventurexp.service;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.model.TimeInterval;
import gruppe3.adventurexp.model.dto.ReservationRequest;
import gruppe3.adventurexp.repository.ActivityRepository;
import gruppe3.adventurexp.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ActivityRepository activityRepository;

    public ReservationService(
            final ReservationRepository reservationRepository,
            final ActivityRepository activityRepository) {
        this.reservationRepository = reservationRepository;
        this.activityRepository = activityRepository;
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation getById(final Long id) {
        return reservationRepository.findById(id)
                .orElseThrow();
    }

    public Reservation save(final ReservationRequest request) {

        final Activity activity = activityRepository
                .findById(request.activityId())
                .orElseThrow();

        final TimeInterval timeInterval =
                new TimeInterval(request.start(), request.end());

        final int price =
                activity.getPrice() * request.amountPeople();

        final Reservation reservation = new Reservation();
        reservation.setActivity(activity);
        reservation.setName(request.name());
        reservation.setPhoneNumber(request.phoneNumber());
        reservation.setAmountPeople(request.amountPeople());
        reservation.setTimeInterval(timeInterval);
        reservation.setPrice(price);

        return reservationRepository.save(reservation);
    }

    public Reservation update(
            final Long id,
            final ReservationRequest request) {

        final Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow();

        final Activity activity = activityRepository
                .findById(request.activityId())
                .orElseThrow();

        final TimeInterval timeInterval =
                new TimeInterval(request.start(), request.end());

        final int price =
                activity.getPrice() * request.amountPeople();

        reservation.setActivity(activity);
        reservation.setName(request.name());
        reservation.setPhoneNumber(request.phoneNumber());
        reservation.setAmountPeople(request.amountPeople());
        reservation.setTimeInterval(timeInterval);
        reservation.setPrice(price);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> reserveAllActivities(Reservation request) {
        List<Reservation> reservations = new ArrayList<>();

        for (Activity activity : activityRepository.findAll()) {
            Reservation r = new Reservation();

            r.setActivity(activity);
            r.setName(request.getName());
            r.setPhoneNumber(request.getPhoneNumber());
            r.setAmountPeople(request.getAmountPeople());
            r.setTimeInterval(request.getTimeInterval());
            r.setPrice(request.getPrice());

            reservations.add(r);
        }

        return reservationRepository.saveAll(reservations);
    }

    public void remove(final Long id) {

        final Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow();

        reservationRepository.delete(reservation);
    }
}