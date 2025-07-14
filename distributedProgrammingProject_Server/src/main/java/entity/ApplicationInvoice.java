package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("Application")
public class ApplicationInvoice extends Invoice implements Serializable {
    private double fee;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = true)
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Override
    public double getFee() {
        return fee;
    }

    @Override
    public Employee getEmployee() {
        return employee;
    }
}
