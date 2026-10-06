package gruppe3.adventurexp.model;

import jakarta.persistence.*;

@Entity
@Table(name="reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int phoneNumber;
    private int amountPeople;
    private int startTime;
    private int endTime;
    private int price;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activities activity;

    public Reservation(){}

    public Reservation(String name, int phoneNumber, int amountPeople, int startTime, int endTime, int price) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.amountPeople = amountPeople;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public Activities getActivity() {
        return activity;
    }

    public void setActivity(Activities activity) {
        this.activity = activity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAmountPeople() {
        return amountPeople;
    }

    public void setAmountPeople(int amountPeople) {
        this.amountPeople = amountPeople;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
