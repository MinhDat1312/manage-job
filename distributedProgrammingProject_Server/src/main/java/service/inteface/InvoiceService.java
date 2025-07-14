package service.inteface;

import entity.Applicant;
import entity.Employee;
import entity.Invoice;
import entity.Recruiter;
import entity.constant.Level;
import entity.constant.WorkingType;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InvoiceService extends GenericService<Invoice, Integer>{
    double getTotalApplicationInvoices() throws RemoteException;
    double getTotalPostedInvoices() throws RemoteException;
    double getTotalInvoicesFee() throws RemoteException;
    Map<Employee, Double> getTotalApplicationInvoicesByEmployee() throws RemoteException;
    Map<Employee, Double> getTotalPostedInvoicesByEmployee() throws RemoteException;
    Map<Recruiter, Long> postedInvoicesByRecruiterStatistics() throws RemoteException;
    List<Invoice> getAllWithApplicantID(long id) throws RemoteException;
    List<Invoice> getAllWithRecruiterID(long id) throws RemoteException;
    Map<WorkingType, Long> statisticsInvoiceByWorkingType() throws RemoteException;
    Map<Level, Long> statisticsInvoiceByLevel() throws RemoteException;
    Map<String, Long> statisticsInvoiceByProfession() throws RemoteException;
    Map<Integer, List<Double>> calculateInvoiceTotalsByMonthAndYear(List<Invoice> invoices, int year) throws RemoteException;
    List<Invoice> getInvoicesWithFilter(Integer recruiterId, Integer applicantId, LocalDate startDate, LocalDate endDate)
        throws RemoteException;
}
