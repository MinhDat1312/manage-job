package view.frame.iframe;

import entity.Job;
import entity.Recruiter;

import java.util.List;

public interface IJobFrame extends IFrame<Job> {
    void showAllRecruiter(List<Recruiter> data);
}
