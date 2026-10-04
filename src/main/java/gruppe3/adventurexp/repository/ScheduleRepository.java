package gruppe3.adventurexp.repository;

import gruppe3.adventurexp.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, LocalDate> {

    List<Schedule> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

}
