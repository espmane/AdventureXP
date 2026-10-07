package gruppe3.adventurexp.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;


@Embeddable
public class Assignment {

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    public Assignment(final Activity activity, final Employee employee) {
        if (activity == null || employee == null) {
            throw new IllegalArgumentException("Ids cant be null");
        }
        this.activity = activity;
        this.employee = employee;
    }

    public Assignment() {}

    public Activity getActivity() {
        return activity;
    }

    public Long getActivityId() {
        return activity.getId();
    }

    public void setActivity(final Activity activity) {
        this.activity = activity;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Long getEmployeeId() {
        return employee.getId();
    }

    public void setEmployee(final Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Assignment that = (Assignment) o;
        return Objects.equals(employee, that.employee) && Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, activity);
    }



}
