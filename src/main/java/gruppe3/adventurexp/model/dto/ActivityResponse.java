package gruppe3.adventurexp.model.dto;

import gruppe3.adventurexp.model.Activity;

public record ActivityResponse(
        Long id,
        String name,
        int price,
        int ageLimit,
        int minParticipants,
        int maxParticipants
) {

    public static ActivityResponse from(final Activity activity) {

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