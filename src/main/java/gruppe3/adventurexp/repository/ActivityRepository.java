package gruppe3.adventurexp.repository;

import gruppe3.adventurexp.model.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityRepository extends JpaRepository<Activities, Long> {
}
