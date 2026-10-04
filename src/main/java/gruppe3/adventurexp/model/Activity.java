package gruppe3.adventurexp.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private int ageLimit;
    private int minParticipants;
    private int maxParticipants;

    public Activity(final Long id, final String name, final int price, final int ageLimit, final int minParticipants, final int maxParticipants) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ageLimit = ageLimit;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
    }

    public Activity() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(final int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(final int minParticipants) {
        this.minParticipants = minParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(final int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Activity activity = (Activity) o;
        return price == activity.price && ageLimit == activity.ageLimit && minParticipants == activity.minParticipants && maxParticipants == activity.maxParticipants && Objects.equals(id, activity.id) && Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, ageLimit, minParticipants, maxParticipants);
    }
}
