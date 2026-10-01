package gruppe3.adventurexp.controller;


import gruppe3.adventurexp.model.Activities;
import gruppe3.adventurexp.repository.ActivityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Activities> result = activityRepository.findById(id);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    @PostMapping("/create")
    public ResponseEntity<Activities> create(@RequestBody Activities.ActivitiesRequest request) {
        Activities newActivity = new Activities(
                request.name(),
                request.price(),
                request.ageLimit(),
                request.minParticipants(),
                request.maxParticipants());

        Activities savedActivity = activityRepository.save(newActivity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedActivity);
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<Activities> edit(@PathVariable int id, @RequestBody Activities.ActivitiesRequest request) {

        return activityRepository.findById(id)
                .map(existingActivity -> {
                    existingActivity.setName(request.name());
                    existingActivity.setPrice(request.price());
                    existingActivity.setAgeLimit(request.ageLimit());
                    existingActivity.setMinParticipants(request.minParticipants());
                    existingActivity.setMaxParticipants(request.maxParticipants());

                    Activities updatedActivity = activityRepository.save(existingActivity);
                    return ResponseEntity.ok(updatedActivity);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return activityRepository.findById(id)
                .map(existingActivity -> {
                    activityRepository.delete(existingActivity);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}