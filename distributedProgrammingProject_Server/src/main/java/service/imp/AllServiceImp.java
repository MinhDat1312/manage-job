package service.imp;

import dao.*;
import entity.*;
import lombok.Getter;
import service.inteface.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Getter
public class AllServiceImp extends UnicastRemoteObject implements AllService {

    private final EmployeeService employeeService = new EmployeeServiceImp(new EmployeeDAO(Employee.class));
    private final AccountService accountService = new AccountServiceImp(new AccountDAO(Account.class));
    private final ApplicantService applicantService = new ApplicantServiceImp(new ApplicantDAO(Applicant.class));
    private final ResumeService resumeService = new ResumeServiceImp(new ResumeDAO(Resume.class));
    private final RecruiterService recruiterService = new RecruiterServiceImp(new RecruiterDAO(Recruiter.class));
    private final JobService jobService = new JobServiceImp(new JobDAO(Job.class));
    private final InvoiceService invoiceService = new InvoiceServiceImp(new InvoiceDAO(Invoice.class));
    private final JobResumeService jobResumeService = new JobResumeServiceImp(new JobResumeDAO(JobResume.class));
    private final ProfessionService professionService = new ProfessionServiceImp(new ProfessionDAO(Profession.class));

    public AllServiceImp() throws RemoteException {
    }
}
