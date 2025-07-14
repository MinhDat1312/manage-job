package service.inteface;

import entity.JobResume;
import entity.constant.Status;

import java.rmi.RemoteException;
import java.util.List;

public interface JobResumeService extends GenericService<JobResume, JobResume.JobResumeId> {
    List<JobResume> getAllWithResumeId(int resumeId) throws RemoteException;
    List<JobResume> getAllWithInfo(int index, Object... info) throws RemoteException;
    List<JobResume> getAllWithStatusRecruiter(Status status, String recruiterName) throws RemoteException;
}
