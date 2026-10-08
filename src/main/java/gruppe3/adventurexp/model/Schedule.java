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
    private List<Reservation> reservationList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "schedule_assignments",
            joinColumns = @JoinColumn(name = "schedule_date"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"schedule_date", "employee_id"}))
    private List<Assignment> assignmentList = new ArrayList<>();

    public Schedule(final LocalDate date, final List<Reservation> reservations, final List<Assignment> assignments) {
        this.date = date;
        for (final Reservation reservation : reservations) addReservation(reservation);
        for (final Assignment assignment : assignments) addAssignment(assignment);
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

    public List<Assignment> getAssignments() {
        return assignmentList;
    }

    public void addAssignment(final Assignment assignment) {
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment cannot be null");
        }

        // throw if the employee is already assigned.
        for (final Assignment existing : assignmentList) {
            if (existing.getEmployee().getId().equals(assignment.getEmployee().getId())) {
                throw new IllegalArgumentException("Employee is already assigned to an activity this day");
            }
        }

        assignmentList.add(assignment);
    }


    public void replaceReservations(final List<Reservation> reservations) {
        reservationList.clear();

        for (final Reservation reservation : reservations) addReservation(reservation);
    }

    public void replaceAssignments(final List<Assignment> list) {
        assignmentList.clear();

        for (final Assignment assignment : list) addAssignment(assignment);
    }

    public void removeReservation(final Reservation reservation) {
        reservationList.remove(reservation);
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

}
