package gruppe3.adventurexp.service;

import org.springframework.stereotype.Service;

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

        final Reservation reservation = new Reservation(
                null,
                activity,
                request.name(),
                request.phoneNumber(),
                request.amountPeople(),
                timeInterval,
                price
        );

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

        for (Activity activity : activiRepository.findAll()) {
            Reservation r = new Reservation(
                    request.getId(),
                    request.getActivity(),
                    request.getName(),
                    request.getPhoneNumber(),
                    request.getAmountPeople(),
                    request.getTimeInterval(),
                    activity.getPrice()
            );
            r.setActivity(activity);
            reservations.add(r);
        }

        return repository.saveAll(reservations);
    }

    public void remove(final Long id) {

        final Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow();

        reservationRepository.delete(reservation);
    }
}