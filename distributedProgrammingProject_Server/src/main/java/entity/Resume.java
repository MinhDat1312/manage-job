package entity;

import jakarta.persistence.*;
import lombok.*;
import entity.constant.Status;
import entity.constant.Level;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Table(name = "resumes")
public class Resume implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "resume_id")
    private int id;
    private String description;
    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "resume_profession",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "profession_id")
    )
    @ToString.Exclude
    private List<Profession> professions;

    @OneToOne
    @JoinColumn(name = "applicant_id", nullable = true)
    private Applicant applicant;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee employee;

    @OneToMany(mappedBy = "resume", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<JobResume> jobResumes;
}