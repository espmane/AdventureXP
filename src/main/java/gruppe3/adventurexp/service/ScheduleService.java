package gruppe3.adventurexp.service;

import gruppe3.adventurexp.mapper.AssignmentMapper;
import gruppe3.adventurexp.mapper.ReservationMapper;
import gruppe3.adventurexp.model.Assignment;
import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.model.Schedule;
import gruppe3.adventurexp.model.dto.ScheduleRequest;
import gruppe3.adventurexp.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ReservationMapper reservationMapper;
    private final AssignmentMapper assignmentMapper;

    public ScheduleService(final ScheduleRepository scheduleRepository,
                           final ReservationMapper reservationMapper,
                           final AssignmentMapper assignmentMapper) {
        this.scheduleRepository = scheduleRepository;
        this.reservationMapper = reservationMapper;
        this.assignmentMapper = assignmentMapper;
    }

    public Schedule getScheduleForDay(final LocalDate date) {
        return scheduleRepository.findById(date)
                .orElseGet(() -> new Schedule(date, List.of(), List.of()));
    }

    public Schedule getScheduleForToday() {
        return getScheduleForDay(LocalDate.now());
    }

    public List<Schedule> getScheduleForWeek(final LocalDate date) {
        final LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        final LocalDate endOfWeek = date.with(DayOfWeek.SUNDAY);

        return scheduleRepository.findAllByDateBetween(startOfWeek, endOfWeek);
    }

    public List<Schedule> getScheduleForMonth(final LocalDate date) {
        final LocalDate startOfMonth = date.withDayOfMonth(1);
        final LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());

        return scheduleRepository.findAllByDateBetween(startOfMonth, endOfMonth);
    }

    @Transactional
    public Schedule save(final ScheduleRequest request) {
        if (scheduleRepository.existsById(request.date())) {
            throw new IllegalArgumentException("A schedule for " + request.date() + " already exists");
        }
        final var schedule = new Schedule(request.date(), toReservations(request), toAssignments(request));

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule update(final ScheduleRequest request) {
        final Schedule schedule = scheduleRepository.findById(request.date())
                .orElseThrow();
        schedule.replaceReservations(toReservations(request));
        schedule.replaceAssignments(toAssignments(request));

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void remove(final LocalDate date) {
        scheduleRepository.deleteById(date);
    }

    private List<Reservation> toReservations(final ScheduleRequest request) {
        return request.reservations().stream()
                .map(reservationMapper::toEntity)
                .toList();
    }

    private List<Assignment> toAssignments(final ScheduleRequest scheduleRequest) {
        return scheduleRequest.assignments().stream()
                .map(assignmentMapper::toEntity)
                .toList();
    }
}
