package gruppe3.adventurexp.model.dto;

import gruppe3.adventurexp.model.Assignment;

public record AssignmentResponse(Long employeeId,
                                 Long activityId) {

    public static AssignmentResponse from(final Assignment assignment) {
        return new AssignmentResponse(
                assignment.getEmployee().getId(),
                assignment.getActivity().getId()
        );
    }
}
