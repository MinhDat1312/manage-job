package service.imp;

import dao.ResumeDAO;
import entity.Job;
import entity.Resume;
import entity.Employee;
import service.inteface.ResumeService;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ResumeServiceImp extends GenericServiceImp<Resume, Integer> implements ResumeService {
    private ResumeDAO resumeDAO;

    public ResumeServiceImp(ResumeDAO dao) throws RemoteException {
        super(dao);
        this.resumeDAO = dao;
    }

    @Override
    public Map<Employee, Long> getTotalResumesByEmployee() throws RemoteException {
        return resumeDAO.getTotalResumesByEmployee();
    }

    @Override
    public List<Resume> findMatchingResumes(Job job) throws RemoteException {
        return resumeDAO.findMatchingResumes(job);
    }

    @Override
    public List<Resume> getByApplicantName(String name) throws RemoteException {
        return resumeDAO.getByApplicantName(name);
    }

    @Override
    public Resume getReusmeByFullNameApplicant(String fullName) throws RemoteException {
        return resumeDAO.getReusmeByFullNameApplicant(fullName);
    }

    @Override
    public List<Resume> findSuitableResumesByJobId(int jobId) throws RemoteException {
        return resumeDAO.findSuitableResumesByJobId(jobId);
    }
}