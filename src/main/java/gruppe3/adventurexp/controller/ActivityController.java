package gruppe3.adventurexp.controller;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.dto.ActivityRequest;
import gruppe3.adventurexp.model.dto.ActivityResponse;
import gruppe3.adventurexp.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(final ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities() {
        final var response = toResponse(activityService.getAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(
            @PathVariable final Long id) {

        final var response =
                ActivityResponse.from(activityService.getById(id));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<ActivityResponse> saveActivity(
            @RequestBody final ActivityRequest request) {

        final var response =
                ActivityResponse.from(activityService.save(request));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<ActivityResponse> updateActivity(
            @PathVariable final Long id,
            @RequestBody final ActivityRequest request) {

        final var response =
                ActivityResponse.from(
                        activityService.update(id, request)
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteActivity(
            @PathVariable final Long id) {

        activityService.remove(id);

        return ResponseEntity.noContent().build();
    }

    private List<ActivityResponse> toResponse(
            final List<Activity> activities) {

        final List<ActivityResponse> responses = new ArrayList<>();

        for (final Activity activity : activities) {
            responses.add(ActivityResponse.from(activity));
        }

        return responses;
    }
}