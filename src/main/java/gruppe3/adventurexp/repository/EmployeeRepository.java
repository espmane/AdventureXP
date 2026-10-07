package gruppe3.adventurexp.repository;

import gruppe3.adventurexp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}


