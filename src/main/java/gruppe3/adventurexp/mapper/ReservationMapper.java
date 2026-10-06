package gruppe3.adventurexp.mapper;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.model.TimeInterval;
import gruppe3.adventurexp.model.dto.ReservationRequest;
import gruppe3.adventurexp.service.ActivityService;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    private final ActivityService activityService;

    public ReservationMapper(final ActivityService activityService) {
        this.activityService = activityService;
    }

    public Reservation toEntity(final ReservationRequest request) {
//        final Activity activity = activityService.findById(request.activityId());
        //TODO: when activityService is done, clean this up
        final Activity activity = new Activity();

        return new Reservation(
                null,
                activity,
                request.name(),
                request.phoneNumber(),
                request.amountPeople(),
                new TimeInterval(request.start(), request.end()),
                activity.getPrice()
        );
    }
}

