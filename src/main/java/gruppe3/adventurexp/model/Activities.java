package gruppe3.adventurexp.model;

import jakarta.persistence.*;

@Entity
@Table(name="activity")
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int price;
    private int ageLimit;
    private int minParticipants;
    private int maxParticipants;

    public Activities(String name, int price, int ageLimit, int minParticipants, int maxParticipants) {
        this.name = name;
        this.price = price;
        this.ageLimit = ageLimit;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
    }

    public Activities(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(int minParticipants) {
        this.minParticipants = minParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }



}
