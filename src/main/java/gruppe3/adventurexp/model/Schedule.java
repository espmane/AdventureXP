package gruppe3.adventurexp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "schedule_id", nullable = false)
    @OrderBy("timeInterval.start")
    private List<Reservation> reservationList;

    public Schedule(final LocalDate date, final List<Reservation> reservations) {
        this.date = date;
        this.reservationList = new ArrayList<>();
        for (final Reservation reservation : reservations) {
            addReservation(reservation);
        }
    }

    public Schedule() {}

    public LocalDate getDate() {
        return date;
    }

    public List<Reservation> getReservations() {
        return reservationList;
    }

    public void addReservation(final Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        // go through all reservation and check if it would overlap
        for (final Reservation r : reservationList) {
            // we only care about reservations with matching activity
            if (!reservation.getActivity().getId().equals(r.getActivity().getId())) {
                continue;
            }

            if (reservation.overlaps(r)) {
                throw new IllegalArgumentException("There is already an reservation of that activity at this time.");
            }
        }
        reservationList.add(reservation);
    }

    public void replaceReservations(final List<Reservation> reservations) {
        reservationList.clear();

        for (final Reservation reservation : reservations) {
            addReservation(reservation);
        }
    }

    public void removeReservation(final Reservation reservation) {
        reservationList.remove(reservation);
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

}
