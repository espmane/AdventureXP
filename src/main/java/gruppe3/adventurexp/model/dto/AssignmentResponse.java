package gruppe3.adventurexp.model.dto;

import gruppe3.adventurexp.model.Assignment;

public record AssignmentResponse(Long employeeId, String employeeName, Long activityId, String activityName) {

    public static AssignmentResponse from(final Assignment assignment) {
        return new AssignmentResponse(
                assignment.getEmployee().getId(),
                assignment.getEmployee().getName(),
                assignment.getActivity().getId(),
                assignment.getActivity().getName()
        );
    }
}
