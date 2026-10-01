package gruppe3.adventurexp.repository;

import gruppe3.adventurexp.model.Activities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activities, Long> {
}
