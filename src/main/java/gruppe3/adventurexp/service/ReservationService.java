package gruppe3.adventurexp.service;

import gruppe3.adventurexp.model.Activity;
import gruppe3.adventurexp.model.Reservation;
import gruppe3.adventurexp.repository.ActivityRepository;
import gruppe3.adventurexp.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final ActivityRepository activiRepository;

    public ReservationService(ReservationRepository repository, ActivityRepository activiRepository){
        this.repository = repository;
        this.activiRepository = activiRepository;
    }



    public List<Reservation> getAllReservations(){
        return repository.findAll();
    }

    public List<Reservation> reserveAllActivities(Reservation request) {
        List<Reservation> reservations = new ArrayList<>();

        for (Activity activity : activiRepository.findAll()) {
            Reservation r = new Reservation(
                    request.getId(),
                    request.getActivity(),
                    request.getName(),
                    request.getPhoneNumber(),
                    request.getAmountPeople(),
                    request.getTimeInterval(),
                    activity.getPrice()
            );
            r.setActivity(activity);
            reservations.add(r);
        }

        return repository.saveAll(reservations);
    }
}
