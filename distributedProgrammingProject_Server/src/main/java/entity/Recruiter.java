package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)

@Table(name = "recruiters")
@AttributeOverride(name = "id", column = @Column(name = "recruiter_id"))
public class Recruiter extends Person implements Serializable {
	private String logo;

	@OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<Job> jobs;

	@OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<PostedInvoice> postedInvoices;

}
