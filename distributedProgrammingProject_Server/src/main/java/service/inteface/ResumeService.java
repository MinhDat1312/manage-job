package service.inteface;

import entity.Job;
import entity.Resume;
import entity.Employee;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ResumeService extends GenericService<Resume, Integer> {
    Map<Employee, Long> getTotalResumesByEmployee() throws RemoteException;
    List<Resume> findMatchingResumes(Job job) throws RemoteException;
    List<Resume> getByApplicantName(String name) throws RemoteException;
    Resume getReusmeByFullNameApplicant(String fullName) throws RemoteException;
    List<Resume> findSuitableResumesByJobId(int jobId) throws RemoteException;
}