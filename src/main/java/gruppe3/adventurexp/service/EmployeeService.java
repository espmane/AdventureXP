package gruppe3.adventurexp.service;

import gruppe3.adventurexp.model.Employee;
import gruppe3.adventurexp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getById(final Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow();
    }
}
