package gruppe3.adventurexp.model.dto;

public record ActivityRequest(
        int id,
        String name,
        int price,
        int ageLimit,
        int minParticipants,
        int maxParticipants
) {
}