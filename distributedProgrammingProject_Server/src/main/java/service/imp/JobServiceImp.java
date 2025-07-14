package service.imp;

import dao.JobDAO;
import entity.Job;
import entity.Profession;
import entity.Resume;
import entity.constant.Level;
import entity.constant.WorkingType;
import service.inteface.JobService;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class JobServiceImp extends GenericServiceImp<Job, Integer> implements JobService {
    private JobDAO jobDAO;

    public JobServiceImp(JobDAO dao) throws RemoteException {
        super(dao);
        this.jobDAO = dao;
    }

    @Override
    public List<Job> getJobsByRecruiter(String recruiterName) throws RemoteException {
        return jobDAO.getJobsByRecruiter(recruiterName);
    }

    @Override
    public Map<Level, Long> levelStatistics() throws RemoteException {
        return jobDAO.levelStatistics();
    }

    @Override
    public Map<Profession, Long> professionStatistics() throws RemoteException {
        return jobDAO.professionStatistics();
    }

    @Override
    public Map<WorkingType, Long> workingTypeStatistics() throws RemoteException {
        return jobDAO.workingTypeStatistics();
    }

    @Override
    public Map<Profession, Map<Integer, Long>> professionMonthStatistics() throws RemoteException {
        return jobDAO.professionMonthStatistics();
    }

    @Override
    public List<Job> findMatchingJobs(Resume resume) throws RemoteException {
        return jobDAO.findMatchingJobs(resume);
    }

    @Override
    public List<Job> getJobsByResumeId(int resumeId) throws RemoteException {
        return jobDAO.getJobsByResumeId(resumeId);
    }

    @Override
    public List<Job> getJobsVisible() throws RemoteException {
        return jobDAO.getJobsVisible();
    }

    @Override
    public List<Job> getJobsByRecruiterVisible(String nameRecruiter) throws RemoteException {
        return jobDAO.getJobsByRecruiterVisible(nameRecruiter);
    }

    @Override
    public List<Job> getJobsWithFilterOption(int option, Object... searchText) throws RemoteException {
        return jobDAO.getJobsWithFilterOption(option, searchText);
    }

    @Override
    public List<Job> getJobsFilter(Object... has) throws RemoteException {
        return jobDAO.getJobsFilter(has);
    }
}