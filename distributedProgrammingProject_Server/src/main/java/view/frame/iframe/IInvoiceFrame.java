package view.frame.iframe;

import entity.Applicant;
import entity.Invoice;
import entity.Recruiter;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public interface IInvoiceFrame extends IFrame<Invoice>{
    void showDataApplicant(List<Applicant> applicants);
    void showDataRecruiter(List<Recruiter> recruiters);
    int getRowTableApplicant();
    int getRowTableRecruiter();
    JTable getTableApplicant();
    JTable getTableRecruiter();
}
