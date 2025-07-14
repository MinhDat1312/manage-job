package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import entity.constant.WorkingType;
import entity.constant.Level;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Table(name = "jobs")
public class Job implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "job_id")
	private int id;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	@Enumerated(EnumType.STRING)
	private Level level;
	private int numberOfPositions;
	private double salary;
	@Enumerated(EnumType.STRING)
	private WorkingType workingType;
	private boolean visible;

	@ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinTable(
			name = "job_profession",
			joinColumns = @JoinColumn(name = "job_id"),
			inverseJoinColumns = @JoinColumn(name = "profession_id")
	)
	@ToString.Exclude
	private List<Profession> professions;

	@ManyToOne
	@JoinColumn(name = "recruiter_id")
	@ToString.Exclude
	private Recruiter recruiter;

	@OneToMany(mappedBy = "job", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<JobResume> jobResumes;

	@OneToOne(mappedBy = "job", fetch = FetchType.EAGER)
	@ToString.Exclude
	private PostedInvoice postedInvoice;

	@OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<ApplicationInvoice> applicationInvoices;
}
