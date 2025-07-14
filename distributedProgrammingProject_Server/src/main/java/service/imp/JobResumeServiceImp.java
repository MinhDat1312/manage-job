package service.imp;

import dao.JobResumeDAO;
import entity.JobResume;
import entity.constant.Status;
import service.inteface.JobResumeService;

import java.rmi.RemoteException;
import java.util.List;

public class JobResumeServiceImp extends GenericServiceImp<JobResume, JobResume.JobResumeId> implements JobResumeService {
    private JobResumeDAO jobResumeDAO;

    public JobResumeServiceImp(JobResumeDAO dao) throws RemoteException {
        super(dao);
        this.jobResumeDAO=dao;
    }

    @Override
    public List<JobResume> getAllWithResumeId(int resumeId) throws RemoteException {
        return jobResumeDAO.getAllWithResumeId(resumeId);
    }

    @Override
    public List<JobResume> getAllWithInfo(int index, Object... info) throws RemoteException {
        return jobResumeDAO.getAllWithInfo(index, info);
    }

    @Override
    public List<JobResume> getAllWithStatusRecruiter(Status status, String recruiterName) throws RemoteException {
        return jobResumeDAO.getAllWithStatusRecruiter(status, recruiterName);
    }
}
