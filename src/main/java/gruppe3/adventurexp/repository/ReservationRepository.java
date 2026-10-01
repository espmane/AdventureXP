package gruppe3.adventurexp.repository;

import gruppe3.adventurexp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
