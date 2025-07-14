package service.imp;

import dao.ApplicantDAO;
import dao.GenericDAO;
import dao.InvoiceDAO;
import dao.RecruiterDAO;
import entity.Applicant;
import entity.Employee;
import entity.Invoice;
import entity.Recruiter;
import entity.constant.Level;
import entity.constant.WorkingType;
import service.inteface.InvoiceService;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class InvoiceServiceImp extends GenericServiceImp<Invoice, Integer> implements InvoiceService {
    private InvoiceDAO invoiceDAO;
    private ApplicantDAO applicantDAO;
    private RecruiterDAO recruiterDAO;

    public InvoiceServiceImp(InvoiceDAO dao) throws RemoteException {
        super(dao);
        this.invoiceDAO = dao;
        this.applicantDAO = new ApplicantDAO(Applicant.class);
        this.recruiterDAO = new RecruiterDAO(Recruiter.class);
    }

    @Override
    public double getTotalApplicationInvoices() throws RemoteException {
        return invoiceDAO.getTotalApplicationInvoices();
    }

    @Override
    public double getTotalPostedInvoices() throws RemoteException {
        return invoiceDAO.getTotalPostedInvoices();
    }

    @Override
    public double getTotalInvoicesFee() throws RemoteException {
        return invoiceDAO.getTotalInvoicesFee();
    }

    @Override
    public Map<Employee, Double> getTotalApplicationInvoicesByEmployee() throws RemoteException {
        return invoiceDAO.getTotalApplicationInvoicesByEmployee();
    }

    @Override
    public Map<Employee, Double> getTotalPostedInvoicesByEmployee() throws RemoteException {
        return invoiceDAO.getTotalPostedInvoicesByEmployee();
    }

    @Override
    public Map<Recruiter, Long> postedInvoicesByRecruiterStatistics() throws RemoteException {
        return invoiceDAO.postedInvoicesByRecruiterStatistics();
    }

    @Override
    public List<Invoice> getAllWithApplicantID(long id) throws RemoteException {
        return invoiceDAO.getAllWithApplicantID(id);
    }

    @Override
    public List<Invoice> getAllWithRecruiterID(long id) throws RemoteException {
        return invoiceDAO.getAllWithRecruiterID(id);
    }

    @Override
    public Map<WorkingType, Long> statisticsInvoiceByWorkingType() throws RemoteException {
        return invoiceDAO.statisticsInvoiceByWorkingType();
    }

    @Override
    public Map<Level, Long> statisticsInvoiceByLevel() throws RemoteException {
        return invoiceDAO.statisticsInvoiceByLevel();
    }

    @Override
    public Map<String, Long> statisticsInvoiceByProfession() throws RemoteException {
        return invoiceDAO.statisticsInvoiceByProfession();
    }

    @Override
    public Map<Integer, List<Double>> calculateInvoiceTotalsByMonthAndYear(List<Invoice> invoices, int year) throws RemoteException {
        return invoiceDAO.calculateInvoiceTotalsByMonthAndYear(invoices, year);
    }

    @Override
    public List<Invoice> getInvoicesWithFilter(Integer recruiterId, Integer applicantId, LocalDate startDate, LocalDate endDate) throws RemoteException {
        return invoiceDAO.getInvoicesWithFilter(recruiterId, applicantId, startDate, endDate);
    }
}
