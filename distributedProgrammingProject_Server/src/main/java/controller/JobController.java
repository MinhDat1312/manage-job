package controller;

import dao.InvoiceDAO;
import dao.RecruiterDAO;
import entity.*;
import entity.constant.Level;
import service.imp.InvoiceServiceImp;
import service.imp.RecruiterServiceImp;
import service.inteface.InvoiceService;
import service.inteface.JobService;
import service.inteface.RecruiterService;
import view.dialog.createupdate.CreateUpdateDetailJobDialog;
import view.frame.iframe.IJobFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.imp.JobFrameImp;

import javax.swing.*;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JobController {
    private final IJobFrame jobFrame;
    private final JobService jobService;
    private RecruiterService recruiterService;
    private InvoiceService invoiceService;
    private final double FEE = 200000.0;

    public JobController(IJobFrame jobFrame, JobService jobService) {
        this.jobFrame = jobFrame;
        this.jobService = jobService;
        try {
            this.recruiterService = new RecruiterServiceImp(new RecruiterDAO(Recruiter.class));
            this.invoiceService = new InvoiceServiceImp(new InvoiceDAO(Invoice.class));
        } catch (RemoteException e) {
            jobFrame.showMessage("Kết nối bị lỗi");
        }

        renewVisibleJob();
        show();
        setListener();
    }

    private void setListener() {
        jobFrame.setFrameListener(new IFrameListener<Job>() {
            @Override
            public void onSearch(Object criteria) {
                Object[] obj = (Object[]) criteria;
                String title = String.valueOf(obj[0]);
                String salary = String.valueOf(obj[1]);
                String recruiter = String.valueOf(obj[2]);
                String level = String.valueOf(obj[3]);

                boolean hasTitle = title != null && !title.equalsIgnoreCase("Nhập dữ liệu");
                boolean hasSalary = salary != null && !salary.equalsIgnoreCase("Nhập dữ liệu");
                boolean hasRecruiter = !recruiter.equalsIgnoreCase("Chọn nhà tuyển dụng");
                boolean hasLevel = !level.equalsIgnoreCase("Chọn trình độ");

                try {
                    List<Job> data = jobService.getJobsFilter(hasTitle, hasSalary, hasRecruiter, hasLevel,
                            title, salary, recruiter, level);
                    jobFrame.showData(data);
                } catch (RemoteException e) {
                    jobFrame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onView(int row) {

            }

            @Override
            public void onAdd() {

            }

            @Override
            public void onUpdate(int row) {
                try {
                    Job job = jobService.findById(row);
                    CreateUpdateDetailJobDialog dialog = new CreateUpdateDetailJobDialog(
                            null, true, job.getRecruiter(), job
                    );
                    dialog.setVisible(true);
                    if(dialog.isConfirmed()){
                        jobService.update(job);
                        show();
                        jobFrame.showMessage("Cập nhật tin tuyển dụng thành công");
                    }
                } catch (RemoteException e) {
                    jobFrame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onDelete(int row) {
                try {
                    int confirm = JOptionPane.showConfirmDialog(null, "Xóa tin tuyển dụng này?");
                    if (confirm == JOptionPane.YES_OPTION){
                        Job job = jobService.findById(row);
                        Recruiter recruiter = job.getRecruiter();
                        if(job.getJobResumes()!=null && job.getJobResumes().size()>0){
                            jobFrame.showMessage("Tin tuyển dụng này có ứng viên ứng tuyển");
                            return;
                        }
                        if((job.getApplicationInvoices()!=null && job.getApplicationInvoices().size()>0)
                                || job.getPostedInvoice()!=null){
                            jobFrame.showMessage("Tin tuyển dụng này có thông tin hóa đơn");
                            return;
                        }

                        List<Profession> professionList = new ArrayList<>(job.getProfessions());
                        for (Profession p : professionList) {
                            p.getJobs().remove(job);
                        }
                        job.getProfessions().clear();

                        recruiter.getJobs().remove(job);
                        job.setRecruiter(null);

                        jobService.delete(row);
                        show();
                        jobFrame.showMessage("Xóa tin tuyển dụng thành công");
                    }
                } catch (RemoteException e) {
                    jobFrame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onExport(Object... object) {
                int id = (Integer) object[0];
                Employee employee = (Employee) object[1];
                int confirm = JOptionPane.showConfirmDialog(null, "Tạo hóa đơn này?");
                if (confirm == JOptionPane.YES_OPTION){
                    try {
                        Job job = jobService.findById(id);

                        Invoice invoice = new PostedInvoice();
                        ((PostedInvoice)invoice).setCreatedDate(LocalDate.now());
                        ((PostedInvoice)invoice).setApplicationCount(job.getJobResumes().size());
                        ((PostedInvoice)invoice).setApplicationFee(FEE);
                        ((PostedInvoice)invoice).setEmployee(employee);
                        ((PostedInvoice)invoice).setRecruiter(job.getRecruiter());
                        ((PostedInvoice)invoice).setJob(job);

                        if (employee.getPostedInvoices() == null) {
                            employee.setPostedInvoices(new ArrayList<>());
                        }
                        employee.getPostedInvoices().add((PostedInvoice) invoice);

                        if(job.getRecruiter().getPostedInvoices() == null){
                            job.getRecruiter().setPostedInvoices(new ArrayList<>());
                        }
                        job.getRecruiter().getPostedInvoices().add((PostedInvoice) invoice);

                        job.setPostedInvoice((PostedInvoice) invoice);

                        invoiceService.create(invoice);
                        show();
                        jobFrame.showMessage("Tạo hóa đơn thành công");
                    } catch (RemoteException e) {
                        jobFrame.showMessage(e.getMessage());
                    }
                }

            }

            @Override
            public void onReset() {
                ((JobFrameImp)jobFrame).addPlaceHolder(((JobFrameImp)jobFrame).getSearchNameText());
                ((JobFrameImp)jobFrame).addPlaceHolder(((JobFrameImp)jobFrame).getSearchSalaryText());
                ((JobFrameImp)jobFrame).getSearchRecruiterBox().setSelectedIndex(0);
                ((JobFrameImp)jobFrame).getSearchLevelBox().setSelectedIndex(0);
                show();
            }
        });
    }

    private void renewVisibleJob(){
        try {
            List<Job> jobList = new ArrayList<>(jobService.getAll());
            for (Job job : jobList) {
                if(!job.getEndDate().isAfter(LocalDate.now())){
                    job.setVisible(false);
                    jobService.update(job);
                }
            }
        } catch (RemoteException e) {
            jobFrame.showMessage("Kết nối bị lỗi");
        }
    }

    private void show() {
        try {
            jobFrame.showData(jobService.getAll());
            jobFrame.showAllRecruiter(recruiterService.getAll());
        } catch (RemoteException e) {
            jobFrame.showMessage(e.getMessage());
        }
    }
}
