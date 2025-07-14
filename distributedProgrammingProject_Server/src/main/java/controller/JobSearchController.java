package controller;

import dao.ApplicantDAO;
import dao.JobResumeDAO;
import dao.RecruiterDAO;
import dao.ResumeDAO;
import entity.*;
import entity.constant.Status;
import service.imp.ApplicantServiceImp;
import service.imp.JobResumeServiceImp;
import service.imp.RecruiterServiceImp;
import service.imp.ResumeServiceImp;
import service.inteface.*;
import view.dialog.createupdate.CreateUpdateDetailJobDialog;
import view.dialog.createupdate.CreateUpdateDetailResumeDialog;
import view.dialog.detail.DetailJobSearchDialog;
import view.frame.iframe.IJobSearchFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.imp.JobSearchFrameImp;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobSearchController {
    private final IJobSearchFrame frame;
    private final JobService jobService;
    private ResumeService resumeService;
    private RecruiterService recruiterService;
    private ApplicantService applicantService;
    private JobResumeService jobResumeService;

    public JobSearchController(IJobSearchFrame frame, JobService jobService) {
        this.frame = frame;
        this.jobService = jobService;
        try {
            this.resumeService = new ResumeServiceImp(new ResumeDAO(Resume.class));
            this.recruiterService = new RecruiterServiceImp(new RecruiterDAO(Recruiter.class));
            this.applicantService = new ApplicantServiceImp(new ApplicantDAO(Applicant.class));
            this.jobResumeService = new JobResumeServiceImp(new JobResumeDAO(JobResume.class));
        } catch (RemoteException e) {
            frame.showMessage("Kết nối bị lỗi");
        }

        renewVisibleJob();
        setListener();
        show();
    }

    private void  setListener() {
        frame.setFrameListener(new IFrameListener<Job>() {
            @Override
            public void onSearch(Object criteria) {
                Object[] values = (Object[]) criteria;
                int id = Integer.parseInt(values[0].toString());
                String name = values[1].toString();

                try{
                    if(name.equals("Resume")){
                        Resume resume = resumeService.findById(id);
                        List<Job> matchingJobs = jobService.findMatchingJobs(resume);
                        frame.showData(matchingJobs);
                    } else if(name.equals("Job")){
                        Job job = jobService.findById(id);
                        List<Resume> matchingResumes = resumeService.findMatchingResumes(job);
                        frame.showDataResume(matchingResumes);
                    }
                } catch (RemoteException e) {
                    frame.showMessage("Lỗi Khi tìm kiếm");
                }

            }

            @Override
            public void onView(int row) {
                try {
                    Resume resume = resumeService.findById(row);
                    Applicant applicant = resume.getApplicant();
                    CreateUpdateDetailResumeDialog dialog = new CreateUpdateDetailResumeDialog(
                            null, true, resume
                    );
                    dialog.setVisible(true);
                } catch (RemoteException e) {
                    frame.showMessage("Lỗi khi xem hồ sơ");
                }
            }

            @Override
            public void onAdd() {}

            @Override
            public void onUpdate(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    Job job = jobService.findById(id);
                    List<Resume> matchingResumes = resumeService.findSuitableResumesByJobId(job.getId());
                    CreateUpdateDetailJobDialog dialog = new CreateUpdateDetailJobDialog(
                            null, true,
                            job, ((JobSearchFrameImp)frame).getEmployee(), matchingResumes
                    );
                    dialog.setVisible(true);
                    if(dialog.isConfirmed()){
                        JobResume res = dialog.getJobResume();
                        Job job1 = jobService.findById(res.getId().getJobId());
                        Resume resume1 = resumeService.findById(res.getId().getResumeId());
                        JobResume jobResume = new JobResume();
                        jobResume.setId(res.getId());
                        jobResume.setJob(job1);
                        jobResume.setResume(resume1);
                        jobResume.setStatus(Status.PENDING);

                        if(job1.getJobResumes() == null){
                            job1.setJobResumes(new ArrayList<>());
                        }
                        job1.getJobResumes().add(jobResume);

                        if(resume1.getJobResumes() == null){
                            resume1.setJobResumes(new ArrayList<>());
                        }
                        resume1.getJobResumes().add(jobResume);

                        jobResumeService.create(jobResume);
                        frame.showMessage("Ứng tuyển thành công");
                    }
                } catch (RemoteException e) {
                    frame.showMessage("Kết nối bị lỗi");
                }
            }

            @Override
            public void onDelete(int row) {}

            @Override
            public void onExport(Object... object) {
                if(Integer.parseInt(object[1].toString()) == 0){
                    try {
                        Resume resume = resumeService.getReusmeByFullNameApplicant(object[0].toString());
                        List<Resume> resumes = new ArrayList<>();
                        resumes.add(resume);
                        frame.showDataResume(resumes);
                    } catch (RemoteException e) {
                        frame.showMessage("Không thể lấy hồ sơ ứng viên");
                    }
                } else {
                    try {
                        List<Job> jobs = jobService.getJobsByRecruiterVisible(object[0].toString());
                        frame.showData(jobs);
                    } catch (RemoteException e) {
                        frame.showMessage("Không thể lấy hồ sơ ứng viên");
                    }
                }
            }

            @Override
            public void onReset() {
                show();
            }
        });
    }

    private void renewVisibleJob(){
        try {
            List<Job> jobList = new ArrayList<>(jobService.getAll());
            for (Job job : jobList) {
                if(!job.getEndDate().isAfter(LocalDate.now())){
                    job.setVisible(false);
                    jobService.update(job);
                }
            }
        } catch (RemoteException e) {
            frame.showMessage("Kết nối bị lỗi");
        }
    }

    public void show() {
        try {
            List<Job> jobs = jobService.getJobsVisible();
            frame.showData(jobs);

            List<Resume> resumes = resumeService.getAll();
            frame.showDataResume(resumes);

            List<Recruiter> recruiters = recruiterService.getAll();
            frame.setRecruiterOptions(recruiters);

            List<Applicant> applicants = applicantService.getAll();
            frame.setApplicantOptions(applicants);
        } catch (Exception e) {
            frame.showMessage("Kết nối bị lỗi");
        }
    }
}