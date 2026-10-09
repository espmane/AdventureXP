package gruppe3.adventurexp.service;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.model.Schedule;
import gruppe3.adventurexp.model.TimeInterval;
import gruppe3.adventurexp.model.dto.ReservationRequest;
import gruppe3.adventurexp.repository.ActivityRepository;
import gruppe3.adventurexp.repository.ReservationRepository;
import gruppe3.adventurexp.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ActivityRepository activityRepository;
    private final ScheduleRepository scheduleRepository;

    public ReservationService(
            final ReservationRepository reservationRepository,
            final ActivityRepository activityRepository,
            final ScheduleRepository scheduleRepository) {
        this.reservationRepository = reservationRepository;
        this.activityRepository = activityRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation getById(final Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Reservation not found: " + id
                        ));
    }

    public Reservation save(final ReservationRequest request) {
        final Activity activity = activityRepository
                .findById(request.activityId())
                .orElseThrow(() ->
                        new NoSuchElementException("Activity not found")
                );

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
        reservation.setPrice(
                reservation.calculatePrice(activity.getPrice())
        );

        final Schedule schedule = scheduleFor(request.start());
        schedule.addReservation(reservation);
        scheduleRepository.saveAndFlush(schedule);
        return reservation;
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

        reservation.setPrice(
                reservation.calculatePrice(activity.getPrice())
        );

        return reservationRepository.save(reservation);
    }

    public List<Reservation> reserveAllActivities(final ReservationRequest request) {

        final List<Reservation> reservations = new ArrayList<>();
        final Schedule schedule = scheduleFor(request.start());

        for (final Activity activity : activityRepository.findAll()) {

            final Reservation reservation = new Reservation();
            reservation.setActivity(activity);
            reservation.setName(request.name());
            reservation.setPhoneNumber(request.phoneNumber());
            reservation.setAmountPeople(request.amountPeople());
            reservation.setTimeInterval(new TimeInterval(request.start(), request.end()));
            reservation.setPrice(reservation.calculatePrice(activity.getPrice()));

            schedule.addReservation(reservation);
            reservations.add(reservation);
        }
        scheduleRepository.saveAndFlush(schedule);
        return reservations;
    }

    public void remove(final Long id) {

        final Reservation reservation = getById(id);

        reservationRepository.delete(reservation);
    }

    private Schedule scheduleFor(final LocalDateTime date) {
        return scheduleRepository.findById(date.toLocalDate())
                .orElseGet(() -> new Schedule(date.toLocalDate(), List.of(), List.of()));
    }
}