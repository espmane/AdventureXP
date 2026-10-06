package gruppe3.adventurexp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;

    public Employee(final String name, final String phoneNumber) {
        this.name = name;
        this.phoneNumber = requireValidPhoneNumber(phoneNumber);
    }

    public Employee() {}

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

    public Long getId() {
        return id;
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
        this.phoneNumber = phoneNumber;
    }
}
