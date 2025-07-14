package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "invoices",
		uniqueConstraints = {
			@UniqueConstraint(name = "uc_application_invoice", columnNames = {"applicant_id", "job_id"}),
			@UniqueConstraint(name = "uc_posted_invoice", columnNames = {"recruiter_id", "job_id"})
		}
)
@DiscriminatorColumn(name = "Discriminator")
public abstract class Invoice implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "invoice_id")
	protected int id;
	protected LocalDate createdDate;

	public abstract double getFee();
	public abstract Employee getEmployee();
}
