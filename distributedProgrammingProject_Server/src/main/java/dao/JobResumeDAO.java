package dao;

import entity.JobResume;
import entity.constant.Status;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JobResumeDAO extends GenericDAO<JobResume, JobResume.JobResumeId> {
    public JobResumeDAO(Class<JobResume> clazz, EntityManager em) {
        super(clazz, em);
    }

    public JobResumeDAO(Class<JobResume> clazz) {
        super(clazz);
    }

    public List<JobResume> getAllWithResumeId(int resumeId) {
        return getAllWithFilter("a.resume.id = ?1", resumeId);
    }

    public List<JobResume> getAllWithInfo(int index, Object... info){
        if (index == 0) {
            return getAllWithFilter("a.resume.applicant.name LIKE ?1 AND a.status = ?2",
                    (String)info[0], (Status)info[1]);
        } else {
            return getAllWithFilter("a.resume.applicant.name LIKE ?1", (String)info[0]);
        }
    }

    public List<JobResume> getAllWithStatusRecruiter(Status status, String recruiterName) {
        return getAllWithJoinFilter(
                "a.job j", "a.status = ?1 AND j.recruiter.name = ?2",
                status, recruiterName
        );
    }
}
