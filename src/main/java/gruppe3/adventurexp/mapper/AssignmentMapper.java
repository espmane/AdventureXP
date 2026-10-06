package gruppe3.adventurexp.mapper;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.Assignment;
import gruppe3.adventurexp.model.Employee;
import gruppe3.adventurexp.model.dto.AssignmentRequest;
import gruppe3.adventurexp.service.ActivityService;
import gruppe3.adventurexp.service.EmployeeService;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {

    private final ActivityService activityService;
    private final EmployeeService employeeService;

    public AssignmentMapper(final ActivityService activityService, final EmployeeService employeeService) {
        this.activityService = activityService;
        this.employeeService = employeeService;
    }

    public Assignment toEntity(final AssignmentRequest assignmentRequest) {
        final Activity activity = activityService.getById(assignmentRequest.activityId());
        final Employee employee = employeeService.getById(assignmentRequest.employeeId());

        return new Assignment(activity, employee);
    }
}
