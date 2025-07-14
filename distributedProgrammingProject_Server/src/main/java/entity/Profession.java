package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "professions")
public class Profession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "profession_id")
    private int id;
    private String name;

    @ManyToMany(mappedBy = "professions", fetch = FetchType.LAZY)
    List<Resume> resumes;

    @ManyToMany(mappedBy = "professions", fetch = FetchType.LAZY)
    List<Job> jobs;
}
