package gruppe3.adventurexp.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    private String name;
    private String phoneNumber;
    private int amountPeople;
    @Embedded
    private TimeInterval timeInterval;
    private int price;

    public Reservation(final Long id, final Activity activity, final String name, final String phoneNumber, final int amountPeople, final TimeInterval timeInterval, final int price) {
        this.id = id;
        this.activity = activity;
        this.name = name;
        this.phoneNumber = requireValidPhoneNumber(phoneNumber);
        this.amountPeople = amountPeople;
        this.timeInterval = timeInterval;
        this.price = price;
    }

    public Reservation() {}

    private static String requireValidPhoneNumber(final String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number can't be blank");
        }

        final var trimmedNumber = phoneNumber.trim();

        if (!trimmedNumber.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Phone number can only contain digits");
        }

        if (trimmedNumber.length() != 8) {
            throw new IllegalArgumentException("Phone number must be 8 digits long");
        }

        return trimmedNumber;
    }

    public boolean overlaps(final Reservation reservation) {
        return this.timeInterval.overlaps(reservation.getTimeInterval());
    }

    public Long getId() {
        return id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(final Activity activity) {
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = requireValidPhoneNumber(phoneNumber);
    }

    public int getAmountPeople() {
        return amountPeople;
    }

    public void setAmountPeople(final int amountPeople) {
        this.amountPeople = amountPeople;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(final TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Reservation that = (Reservation) o;
        return amountPeople == that.amountPeople && price == that.price && Objects.equals(id, that.id) && Objects.equals(activity, that.activity) && Objects.equals(name, that.name) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(timeInterval, that.timeInterval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activity, name, phoneNumber, amountPeople, timeInterval, price);
    }
}
