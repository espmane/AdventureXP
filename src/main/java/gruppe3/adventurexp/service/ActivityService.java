package gruppe3.adventurexp.service;

import gruppe3.adventurexp.model.Activities;
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

    public List<Activities> getAll() {
        return activityRepository.findAll();
    }

    public Activities getById(final int id) {
        return activityRepository.findById(id)
                .orElseThrow();
    }

    public Activities save(final ActivityRequest request) {

        final Activities activity = new Activities(
                request.name(),
                request.price(),
                request.ageLimit(),
                request.minParticipants(),
                request.maxParticipants()
        );

        return activityRepository.save(activity);
    }

    public Activities update(final ActivityRequest request) {

        final Activities activity = activityRepository
                .findById(request.id())
                .orElseThrow();

        activity.setName(request.name());
        activity.setPrice(request.price());
        activity.setAgeLimit(request.ageLimit());
        activity.setMinParticipants(request.minParticipants());
        activity.setMaxParticipants(request.maxParticipants());

        return activityRepository.save(activity);
    }

    public void remove(final int id) {

        final Activities activity = activityRepository
                .findById(id)
                .orElseThrow();

        activityRepository.delete(activity);
    }
}