package entity;
import entity.constant.Status;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "job_resume")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JobResume implements Serializable {
    @EmbeddedId
    private JobResumeId id;

    @ManyToOne
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    @ToString.Exclude
    private Job job;

    @ManyToOne
    @MapsId("resumeId")
    @JoinColumn(name = "resume_id")
    @ToString.Exclude
    private Resume resume;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Embeddable
    @Setter
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class JobResumeId implements java.io.Serializable {
        @Column(name = "job_id")
        private int jobId;
        @Column(name = "resume_id")
        private int resumeId;
    }
}

