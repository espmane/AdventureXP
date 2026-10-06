package gruppe3.adventurexp.service;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.dto.ActivityRequest;
import gruppe3.adventurexp.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(final ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

    public Activity getById(final Long id) {
        return activityRepository.findById(id)
                .orElseThrow();
    }

    public Activity save(final ActivityRequest request) {

        final Activity activity = new Activity(
                null,
                request.name(),
                request.price(),
                request.ageLimit(),
                request.minParticipants(),
                request.maxParticipants()
        );

        return activityRepository.save(activity);
    }

    public Activity update(
            final Long id,
            final ActivityRequest request) {

        final Activity activity = activityRepository
                .findById(id)
                .orElseThrow();

        activity.setName(request.name());
        activity.setPrice(request.price());
        activity.setAgeLimit(request.ageLimit());
        activity.setMinParticipants(request.minParticipants());
        activity.setMaxParticipants(request.maxParticipants());

        return activityRepository.save(activity);
    }

    public void remove(final Long id) {

        final Activity activity = activityRepository
                .findById(id)
                .orElseThrow();

        activityRepository.delete(activity);
    }
}