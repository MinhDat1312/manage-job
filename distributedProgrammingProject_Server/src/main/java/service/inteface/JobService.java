package service.inteface;

import entity.Job;
import entity.Profession;
import entity.Resume;
import entity.constant.Level;
import entity.constant.WorkingType;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface JobService extends GenericService<Job, Integer> {
    List<Job> getJobsByRecruiter(String recruiterName) throws RemoteException;
    Map<Level, Long> levelStatistics() throws RemoteException;
    Map<Profession, Long> professionStatistics() throws RemoteException;
    Map<WorkingType, Long> workingTypeStatistics() throws RemoteException;
    Map<Profession, Map<Integer, Long>> professionMonthStatistics() throws RemoteException;
    List<Job> findMatchingJobs(Resume resume) throws RemoteException;
    List<Job> getJobsByResumeId(int resumeId) throws RemoteException;
    List<Job> getJobsVisible() throws RemoteException;
    List<Job> getJobsByRecruiterVisible(String nameRecruiter) throws RemoteException;
    List<Job> getJobsWithFilterOption(int option, Object... searchText) throws RemoteException;
    List<Job> getJobsFilter(Object... has) throws RemoteException;
}