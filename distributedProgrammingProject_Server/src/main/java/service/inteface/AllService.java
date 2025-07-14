package service.inteface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AllService extends Remote {
    EmployeeService getEmployeeService() throws RemoteException;

    public AccountService getAccountService() throws RemoteException;

    public ApplicantService getApplicantService() throws RemoteException;

    public ResumeService getResumeService() throws RemoteException;

    public RecruiterService getRecruiterService() throws RemoteException;

    public JobService getJobService() throws RemoteException;

    public InvoiceService getInvoiceService() throws RemoteException;

    public JobResumeService getJobResumeService() throws RemoteException;

    public ProfessionService getProfessionService() throws RemoteException;
}
