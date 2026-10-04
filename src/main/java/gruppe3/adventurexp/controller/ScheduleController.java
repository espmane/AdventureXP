package gruppe3.adventurexp.controller;

import gruppe3.adventurexp.model.Schedule;
import gruppe3.adventurexp.model.dto.ScheduleRequest;
import gruppe3.adventurexp.model.dto.ScheduleResponse;
import gruppe3.adventurexp.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/day")
    public ResponseEntity<ScheduleResponse> getScheduleForDay(@RequestParam final LocalDate date) {
        final var response = ScheduleResponse.from(scheduleService.getScheduleForDay(date));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/today")
    public ResponseEntity<ScheduleResponse> getScheduleForToday() {
        final var response = ScheduleResponse.from(scheduleService.getScheduleForToday());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/week")
    public ResponseEntity<List<ScheduleResponse>> getScheduleForWeek() {
        final var response = toResponse(scheduleService.getScheduleForWeek(LocalDate.now()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/month")
    public ResponseEntity<List<ScheduleResponse>> getScheduleForMonth() {
        final var response = toResponse(scheduleService.getScheduleForMonth(LocalDate.now()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ScheduleResponse> updateSchedule(@RequestBody final ScheduleRequest request) {
        final var response = ScheduleResponse.from(scheduleService.update(request));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ScheduleResponse> saveSchedule(@RequestBody final ScheduleRequest request) {
        final var response = ScheduleResponse.from(scheduleService.save(request));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSchedule(@RequestParam final LocalDate date) {
        scheduleService.remove(date);
        return ResponseEntity.noContent().build();
    }

    private List<ScheduleResponse> toResponse(final List<Schedule> schedules) {
        final List<ScheduleResponse> responses = new ArrayList<>();

        for (final Schedule schedule : schedules) {
            responses.add(ScheduleResponse.from(schedule));
        }
        return responses;
    }
}
