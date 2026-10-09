
package gruppe3.adventurexp.service;

import gruppe3.adventurexp.mapper.AssignmentMapper;
import gruppe3.adventurexp.mapper.ReservationMapper;
import gruppe3.adventurexp.model.Schedule;
import gruppe3.adventurexp.model.dto.ScheduleRequest;
import gruppe3.adventurexp.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    private ScheduleRepository scheduleRepository;
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        scheduleRepository = mock(ScheduleRepository.class);

        ReservationMapper reservationMapper = mock(ReservationMapper.class);
        AssignmentMapper assignmentMapper = mock(AssignmentMapper.class);

        scheduleService = new ScheduleService(
                scheduleRepository,
                reservationMapper,
                assignmentMapper
        );
    }

    @Test
    void getScheduleForDay_returnsExistingSchedule() {
        LocalDate date = LocalDate.of(2026, 10, 9);
        Schedule schedule = new Schedule(date, List.of(), List.of());

        when(scheduleRepository.findById(date))
                .thenReturn(Optional.of(schedule));

        Schedule result = scheduleService.getScheduleForDay(date);

        assertSame(schedule, result);
        verify(scheduleRepository).findById(date);
    }

    @Test
    void getScheduleForDay_returnsEmptyScheduleWhenNotFound() {
        LocalDate date = LocalDate.of(2026, 10, 9);

        when(scheduleRepository.findById(date))
                .thenReturn(Optional.empty());

        Schedule result = scheduleService.getScheduleForDay(date);

        assertNotNull(result);
        verify(scheduleRepository).findById(date);
    }

    @Test
    void getScheduleForToday_usesCurrentDate() {
        LocalDate today = LocalDate.now();

        when(scheduleRepository.findById(today))
                .thenReturn(Optional.empty());

        Schedule result = scheduleService.getScheduleForToday();

        assertNotNull(result);
        verify(scheduleRepository).findById(today);
    }

    @Test
    void getScheduleForWeek_usesMondayAndSunday() {
        LocalDate date = LocalDate.of(2026, 10, 9);

        LocalDate monday = LocalDate.of(2026, 10, 5);
        LocalDate sunday = LocalDate.of(2026, 10, 11);

        when(scheduleRepository.findAllByDateBetween(monday, sunday))
                .thenReturn(List.of());

        List<Schedule> result = scheduleService.getScheduleForWeek(date);

        assertTrue(result.isEmpty());

        verify(scheduleRepository)
                .findAllByDateBetween(monday, sunday);
    }

    @Test
    void getScheduleForMonth_usesFirstAndLastDay() {
        LocalDate date = LocalDate.of(2026, 10, 9);

        LocalDate firstDay = LocalDate.of(2026, 10, 1);
        LocalDate lastDay = LocalDate.of(2026, 10, 31);

        when(scheduleRepository.findAllByDateBetween(firstDay, lastDay))
                .thenReturn(List.of());

        List<Schedule> result = scheduleService.getScheduleForMonth(date);

        assertTrue(result.isEmpty());

        verify(scheduleRepository)
                .findAllByDateBetween(firstDay, lastDay);
    }

    @Test
    void saveSchedule_savesNewSchedule() {
        LocalDate date = LocalDate.of(2026, 10, 9);

        ScheduleRequest request = new ScheduleRequest(
                date,
                List.of(),
                List.of()
        );

        when(scheduleRepository.existsById(date))
                .thenReturn(false);

        when(scheduleRepository.save(any(Schedule.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Schedule result = scheduleService.save(request);

        assertNotNull(result);

        verify(scheduleRepository).existsById(date);
        verify(scheduleRepository).save(any(Schedule.class));
    }

    @Test
    void saveSchedule_throwsExceptionWhenAlreadyExists() {
        LocalDate date = LocalDate.of(2026, 10, 9);

        ScheduleRequest request = new ScheduleRequest(
                date,
                List.of(),
                List.of()
        );

        when(scheduleRepository.existsById(date))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> scheduleService.save(request)
        );

        verify(scheduleRepository, never())
                .save(any(Schedule.class));
    }

    @Test
    void updateSchedule_updatesExistingSchedule() {
        LocalDate date = LocalDate.of(2026, 10, 9);

        ScheduleRequest request = new ScheduleRequest(
                date,
                List.of(),
                List.of()
        );

        Schedule schedule = new Schedule(date, List.of(), List.of());

        when(scheduleRepository.findById(date))
                .thenReturn(Optional.of(schedule));

        when(scheduleRepository.save(any(Schedule.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Schedule result = scheduleService.update(request);

        assertSame(schedule, result);

        verify(scheduleRepository).findById(date);
        verify(scheduleRepository).save(schedule);
    }

    @Test
    void updateSchedule_throwsExceptionWhenNotFound() {
        LocalDate date = LocalDate.of(2026, 10, 9);

        ScheduleRequest request = new ScheduleRequest(
                date,
                List.of(),
                List.of()
        );

        when(scheduleRepository.findById(date))
                .thenReturn(Optional.empty());

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> scheduleService.update(request)
        );

        verify(scheduleRepository, never())
                .save(any(Schedule.class));
    }

    @Test
    void removeSchedule_deletesByDate() {
        LocalDate date = LocalDate.of(2026, 10, 9);

        scheduleService.remove(date);

        verify(scheduleRepository).deleteById(date);
    }
}
