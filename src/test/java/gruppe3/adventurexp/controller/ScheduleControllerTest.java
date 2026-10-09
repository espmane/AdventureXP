
package gruppe3.adventurexp.controller;

import gruppe3.adventurexp.model.Schedule;
import gruppe3.adventurexp.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class ScheduleControllerTest {

    private ScheduleService scheduleService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        scheduleService = mock(ScheduleService.class);

        mockMvc = standaloneSetup(
                new ScheduleController(scheduleService)
        ).build();
    }

    @Test
    void getScheduleForDay_returns200() throws Exception {
        LocalDate date = LocalDate.of(2026, 10, 9);
        Schedule schedule = new Schedule(date, List.of(), List.of());

        when(scheduleService.getScheduleForDay(date))
                .thenReturn(schedule);

        mockMvc.perform(get("/schedule/day")
                        .param("date", "2026-10-09"))
                .andExpect(status().isOk());

        verify(scheduleService).getScheduleForDay(date);
    }

    @Test
    void getScheduleForToday_returns200() throws Exception {
        LocalDate today = LocalDate.now();
        Schedule schedule = new Schedule(today, List.of(), List.of());

        when(scheduleService.getScheduleForToday())
                .thenReturn(schedule);

        mockMvc.perform(get("/schedule/today"))
                .andExpect(status().isOk());

        verify(scheduleService).getScheduleForToday();
    }

    @Test
    void getScheduleForWeek_returns200() throws Exception {
        when(scheduleService.getScheduleForWeek(any(LocalDate.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/schedule/week"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(scheduleService).getScheduleForWeek(any(LocalDate.class));
    }

    @Test
    void getScheduleForMonth_returns200() throws Exception {
        when(scheduleService.getScheduleForMonth(any(LocalDate.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/schedule/month"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(scheduleService).getScheduleForMonth(any(LocalDate.class));
    }

    @Test
    void saveSchedule_callsService() throws Exception {
        LocalDate date = LocalDate.of(2026, 10, 9);
        Schedule schedule = new Schedule(date, List.of(), List.of());

        when(scheduleService.save(any()))
                .thenReturn(schedule);

        mockMvc.perform(post("/schedule/save")
                        .contentType("application/json")
                        .content("""
                                {
                                  "date": "2026-10-09",
                                  "reservations": [],
                                  "assignments": []
                                }
                                """))
                .andExpect(status().isOk());

        verify(scheduleService).save(any());
    }

    @Test
    void updateSchedule_callsService() throws Exception {
        LocalDate date = LocalDate.of(2026, 10, 9);
        Schedule schedule = new Schedule(date, List.of(), List.of());

        when(scheduleService.update(any()))
                .thenReturn(schedule);

        mockMvc.perform(post("/schedule/update")
                        .contentType("application/json")
                        .content("""
                                {
                                  "date": "2026-10-09",
                                  "reservations": [],
                                  "assignments": []
                                }
                                """))
                .andExpect(status().isOk());

        verify(scheduleService).update(any());
    }

    @Test
    void deleteSchedule_returns204() throws Exception {
        LocalDate date = LocalDate.of(2026, 10, 9);

        mockMvc.perform(delete("/schedule/delete")
                        .param("date", "2026-10-09"))
                .andExpect(status().isNoContent());

        verify(scheduleService).remove(date);
    }
}
