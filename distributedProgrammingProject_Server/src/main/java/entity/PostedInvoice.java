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
@DiscriminatorValue("Posted")
public class PostedInvoice extends Invoice implements Serializable {

    private int applicationCount;
    private double applicationFee;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = true)
    private Recruiter recruiter;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Override
    public double getFee() {
        return applicationCount * applicationFee;
    }

    @Override
    public Employee getEmployee() {
        return employee;
    }
}
