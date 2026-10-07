package gruppe3.adventurexp.service;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.model.TimeInterval;
import gruppe3.adventurexp.model.dto.ReservationRequest;
import gruppe3.adventurexp.repository.ActivityRepository;
import gruppe3.adventurexp.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ActivityRepository activityRepository;

    private ReservationService reservationService;

    private Activity activity;
    private Reservation reservation;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        reservationService = new ReservationService(
                reservationRepository,
                activityRepository
        );

        activity = new Activity(
                1L,
                "Gocart",
                200,
                12,
                2,
                10
        );

        TimeInterval timeInterval = new TimeInterval(
                LocalDateTime.of(2026, 10, 10, 10, 0),
                LocalDateTime.of(2026, 10, 10, 11, 0)
        );

        reservation = new Reservation();
        reservation.setActivity(activity);
        reservation.setName("Tom");
        reservation.setPhoneNumber("12345678");
        reservation.setAmountPeople(4);
        reservation.setTimeInterval(timeInterval);
        reservation.setPrice(800);
    }

    @Test
    void getAllReturnsReservations() {

        when(reservationRepository.findAll())
                .thenReturn(List.of(reservation));

        List<Reservation> result = reservationService.getAll();

        assertEquals(1, result.size());
        assertEquals("Tom", result.get(0).getName());

        verify(reservationRepository).findAll();
    }

    @Test
    void getByIdReturnsReservation() {

        when(reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));

        Reservation result = reservationService.getById(1L);

        assertEquals("Tom", result.getName());
        assertEquals("12345678", result.getPhoneNumber());
        assertEquals(4, result.getAmountPeople());

        verify(reservationRepository).findById(1L);
    }

    @Test
    void saveCreatesReservation() {

        ReservationRequest request = new ReservationRequest(
                1L,
                "Tom",
                "12345678",
                4,
                LocalDateTime.of(2026, 10, 10, 10, 0),
                LocalDateTime.of(2026, 10, 10, 11, 0)
        );

        when(activityRepository.findById(1L))
                .thenReturn(Optional.of(activity));

        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation result = reservationService.save(request);

        assertEquals("Tom", result.getName());
        assertEquals("12345678", result.getPhoneNumber());
        assertEquals(4, result.getAmountPeople());
        assertEquals(800, result.getPrice());
        assertEquals(activity, result.getActivity());

        verify(activityRepository).findById(1L);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void updateChangesReservation() {

        ReservationRequest request = new ReservationRequest(
                1L,
                "Peter",
                "87654321",
                5,
                LocalDateTime.of(2026, 10, 10, 12, 0),
                LocalDateTime.of(2026, 10, 10, 13, 0)
        );

        when(reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));

        when(activityRepository.findById(1L))
                .thenReturn(Optional.of(activity));

        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation result = reservationService.update(1L, request);

        assertEquals("Peter", result.getName());
        assertEquals("87654321", result.getPhoneNumber());
        assertEquals(5, result.getAmountPeople());
        assertEquals(1000, result.getPrice());

        verify(reservationRepository).save(reservation);
    }

    @Test
    void removeDeletesReservation() {

        when(reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));

        reservationService.remove(1L);

        verify(reservationRepository).findById(1L);
        verify(reservationRepository).delete(reservation);
    }

    @Test
    void reserveAllActivitiesCreatesReservationForEveryActivity() {

        Activity paintball = new Activity(
                2L,
                "Paintball",
                150,
                14,
                4,
                20
        );

        when(activityRepository.findAll())
                .thenReturn(List.of(activity, paintball));

        when(reservationRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<Reservation> result =
                reservationService.reserveAllActivities(reservation);

        assertEquals(2, result.size());

        assertEquals(
                "Gocart",
                result.get(0).getActivity().getName()
        );

        assertEquals(
                "Paintball",
                result.get(1).getActivity().getName()
        );

        verify(activityRepository).findAll();
        verify(reservationRepository).saveAll(anyList());
    }
}