package entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "applicants")
@AttributeOverride(name = "id", column = @Column(name = "applicant_id"))
public class Applicant extends Person implements Serializable {
    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Resume resume;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ApplicationInvoice> applicationInvoices;
}