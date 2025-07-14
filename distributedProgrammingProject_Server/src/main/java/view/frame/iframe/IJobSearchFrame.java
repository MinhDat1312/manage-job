package view.frame.iframe;

import entity.Applicant;
import entity.Job;
import entity.Recruiter;
import entity.Resume;

import java.util.List;
import java.util.Map;

public interface IJobSearchFrame extends IFrame<Job> {
    void showDataResume(List<Resume> resumes);
    void setRecruiterOptions(List<Recruiter> recruiters);
    void setApplicantOptions(List<Applicant> applicants);
}
