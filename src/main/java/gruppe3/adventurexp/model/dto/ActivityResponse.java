package gruppe3.adventurexp.model.dto;

import gruppe3.adventurexp.model.Activities;

public record ActivityResponse(
        int id,
        String name,
        int price,
        int ageLimit,
        int minParticipants,
        int maxParticipants
) {

    public static ActivityResponse from(Activities activity) {
        return new ActivityResponse(
                activity.getId(),
                activity.getName(),
                activity.getPrice(),
                activity.getAgeLimit(),
                activity.getMinParticipants(),
                activity.getMaxParticipants()
        );
    }
}