package gruppe3.adventurexp.controller;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.model.TimeInterval;
import gruppe3.adventurexp.model.dto.ReservationRequest;
import gruppe3.adventurexp.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    private Reservation reservation;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {

        session = new MockHttpSession();
        session.setAttribute("loggedIn", true);

        Activity activity = new Activity(
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
    void getAllReservationsReturnsOk() throws Exception {

        when(reservationService.getAll())
                .thenReturn(List.of(reservation));

        mockMvc.perform(get("/reservations")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].phoneNumber").value("12345678"))
                .andExpect(jsonPath("$[0].amountPeople").value(4))
                .andExpect(jsonPath("$[0].price").value(800));
    }

    @Test
    void getReservationByIdReturnsOk() throws Exception {

        when(reservationService.getById(1L))
                .thenReturn(reservation);

        mockMvc.perform(get("/reservations/1")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityId").value(1))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.phoneNumber").value("12345678"))
                .andExpect(jsonPath("$.amountPeople").value(4))
                .andExpect(jsonPath("$.price").value(800));
    }

    @Test
    void saveReservationReturnsOk() throws Exception {

        when(reservationService.save(any(ReservationRequest.class)))
                .thenReturn(reservation);

        String json = """
                {
                    "activityId": 1,
                    "name": "Tom",
                    "phoneNumber": "12345678",
                    "amountPeople": 4,
                    "start": "2026-10-10T10:00:00",
                    "end": "2026-10-10T11:00:00"
                }
                """;

        mockMvc.perform(post("/reservations/save")
                        .session(session)
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.phoneNumber").value("12345678"))
                .andExpect(jsonPath("$.amountPeople").value(4))
                .andExpect(jsonPath("$.price").value(800));
    }

    @Test
    void updateReservationReturnsOk() throws Exception {

        when(reservationService.update(
                eq(1L),
                any(ReservationRequest.class)
        )).thenReturn(reservation);

        String json = """
                {
                    "activityId": 1,
                    "name": "Tom",
                    "phoneNumber": "12345678",
                    "amountPeople": 4,
                    "start": "2026-10-10T10:00:00",
                    "end": "2026-10-10T11:00:00"
                }
                """;

        mockMvc.perform(post("/reservations/1/update")
                        .session(session)
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.amountPeople").value(4))
                .andExpect(jsonPath("$.price").value(800));
    }

    @Test
    void deleteReservationReturnsNoContent() throws Exception {

        mockMvc.perform(delete("/reservations/1/delete")
                        .session(session))
                .andExpect(status().isNoContent());

        verify(reservationService).remove(1L);
    }
}