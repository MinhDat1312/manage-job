package controller;

import dao.ApplicantDAO;
import dao.RecruiterDAO;
import entity.Applicant;
import entity.ApplicationInvoice;
import entity.Invoice;
import entity.Recruiter;
import service.imp.ApplicantServiceImp;
import service.imp.RecruiterServiceImp;
import service.inteface.ApplicantService;
import service.inteface.InvoiceService;
import service.inteface.RecruiterService;
import view.dialog.detail.DetailApplicationInvoiceDialog;
import view.dialog.detail.DetailPostedInvoiceDialog;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.iframe.IInvoiceFrame;
import view.frame.imp.InvoiceFrameImp;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class InvoiceController {
    private final IInvoiceFrame frame;
    private final InvoiceService invoiceService;
    private ApplicantService applicantService;
    private RecruiterService recruiterService;

    public InvoiceController(IInvoiceFrame frame, InvoiceService invoiceService) {
        this.frame = frame;
        this.invoiceService = invoiceService;

        try {
            this.applicantService = new ApplicantServiceImp(new ApplicantDAO(Applicant.class));
            this.recruiterService = new RecruiterServiceImp(new RecruiterDAO(Recruiter.class));
        } catch (RemoteException e) {
            frame.showMessage("Kết nối bị lỗi");
        }

        show();
        setFrameListeners();
    }

    private void setFrameListeners(){
        frame.setFrameListener(new IFrameListener<Invoice>() {
            @Override
            public void onSearch(Object criteria) {
                try {
                    Object[] obj = (Object[]) criteria;
                    int rowApplicant = (Integer) obj[2];
                    int rowRecruiter = (Integer) obj[3];
                    Date start = (Date) obj[0];
                    Date end = (Date) obj[1];

                    LocalDate startDate = start == null ? null
                            : start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate endDate = end == null ? null
                            : end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if(startDate != null && endDate != null && startDate.isAfter(endDate)) {
                        frame.showMessage("Khoảng thời gian không hợp lệ");
                        return;
                    }

                    Integer recruiterId = rowRecruiter >= 0
                            ? (Integer) frame.getTableRecruiter().getValueAt(rowRecruiter, 0) : null;
                    Integer applicantId = rowApplicant >= 0
                            ? (Integer) frame.getTableApplicant().getValueAt(rowApplicant, 0) : null;
                    List<Invoice> data;

                    if (recruiterId == null && applicantId == null && startDate == null && endDate == null) {
                        data = invoiceService.getAll();
                    } else {
                        data = invoiceService.getInvoicesWithFilter(recruiterId, applicantId, startDate, endDate);
                    }

                    frame.showData(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onView(int row) {
                int idInvoice = (int) frame.getTable().getValueAt(row, 0);
                try {
                    Invoice invoice = invoiceService.findById(idInvoice);
                    if(invoice instanceof ApplicationInvoice){
                        DetailApplicationInvoiceDialog dialog = new DetailApplicationInvoiceDialog(
                                (InvoiceFrameImp)frame, true, invoice);
                        dialog.setVisible(true);
                    } else {
                        DetailPostedInvoiceDialog dialog = new DetailPostedInvoiceDialog(
                                (InvoiceFrameImp)frame, true, invoice
                        );
                        dialog.setVisible(true);
                    }

                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onAdd() {

            }

            @Override
            public void onUpdate(int row) {

            }

            @Override
            public void onDelete(int row) {

            }

            @Override
            public void onExport(Object... object) {

            }

            @Override
            public void onReset() {
                ((InvoiceFrameImp) frame).getModelStartDate().setValue(null);
                ((InvoiceFrameImp) frame).getModelEndDate().setValue(null);
                show();
            }
        });
    }

    public void show(){
        try {
            frame.showDataApplicant(applicantService.getAll());
            frame.showDataRecruiter(recruiterService.getAll());
            frame.showData(invoiceService.getAll());
        } catch (RemoteException e) {
            frame.showMessage(e.getMessage());
        }
    }

}
