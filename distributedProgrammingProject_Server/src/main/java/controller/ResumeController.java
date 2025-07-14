package controller;

import dao.InvoiceDAO;
import dao.JobDAO;
import dao.JobResumeDAO;
import dao.RecruiterDAO;
import entity.*;
import entity.constant.Status;
import service.imp.InvoiceServiceImp;
import service.imp.JobResumeServiceImp;
import service.imp.JobServiceImp;
import service.imp.RecruiterServiceImp;
import service.inteface.*;
import util.ExcelHelper;
import view.dialog.createupdate.CreateUpdateDetailResumeDialog;
import view.dialog.idialog.IDialogListener;
import view.dialog.list.ListResumesDialog;
import view.frame.iframe.IResumeFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.imp.ResumeFrameImp;

import javax.swing.*;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeController {
    private final IResumeFrame frame;
    private final ResumeService resumeService;
    private JobResumeService jobResumeService;
    private RecruiterService recruiterService;
    private InvoiceService invoiceService;

    private final int PERCENT_1 = 2;
    private final int PERCENT_2 = 3;
    private final int PERCENT_3 = 5;
    private final double SALARY_GT5 = 5000000.0;
    private final double SALARY_GTE10 = 10000000.0;

    public ResumeController(IResumeFrame frame, ResumeService resumeService) {
        this.frame = frame;
        this.resumeService = resumeService;
        try {
            this.jobResumeService = new JobResumeServiceImp(new JobResumeDAO(JobResume.class));
            this.recruiterService = new RecruiterServiceImp(new RecruiterDAO(Recruiter.class));
            this.invoiceService = new InvoiceServiceImp(new InvoiceDAO(Invoice.class));
        } catch (RemoteException e) {
            frame.showMessage("Kết nối bị lỗi");
        }

        show();
        setListener();
    }

    private void setListener() {
        frame.setFrameListener(new IFrameListener<Resume>() {
            @Override
            public void onSearch(Object criteria) {
                Object[] objects = (Object[]) criteria;
                int index = (Integer) objects[0];
                String nameApplicant = (String) objects[1];

                try {
                    List<Resume> resumes = resumeService.getByApplicantName(nameApplicant);
                    frame.showData(resumes);
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onView(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    List<JobResume> jobResumes = jobResumeService.getAllWithResumeId(id);
                    if(jobResumes.size() > 0){
                        ListResumesDialog dialog = new ListResumesDialog(null, true,
                                jobResumes.get(0).getResume().getApplicant());
                        dialog.loadDataRecruiter(recruiterService.getAll());
                        dialog.showData(jobResumes);
                        dialog.setDialogListener(new IDialogListener<JobResume>() {
                            @Override
                            public void onSearch(Object criteria) {
                                Object[] objects = (Object[]) criteria;
                                Status status = null;
                                for (Status s : Status.class.getEnumConstants()) {
                                    if (s.getValue().equalsIgnoreCase((String) objects[0])) {
                                        status = s;
                                        break;
                                    }
                                }
                                String nameRecruiter = (String) objects[1];
                                try {
                                    List<JobResume> jobResumes = jobResumeService.getAllWithStatusRecruiter(
                                            status, nameRecruiter
                                    );
                                    dialog.showData(jobResumes);
                                } catch (RemoteException e) {
                                    dialog.showMessage(e.getMessage());
                                }
                            }

                            @Override
                            public void onView(int row) {
                                String jobTitle = dialog.getTable().getValueAt(row, 3).toString();
                                JobResume jobResume = null;
                                for (JobResume j: jobResumes){
                                    if(j.getJob().getTitle().equals(jobTitle)){
                                        jobResume = j;
                                        break;
                                    }
                                }
                                CreateUpdateDetailResumeDialog dialog2 = new CreateUpdateDetailResumeDialog(null, true,
                                        ((ResumeFrameImp)frame).getEmpployee(), jobResume);
                                dialog2.setVisible(true);
                                if(dialog2.isConfirmed()){
                                    try {
                                        jobResumeService.update(dialog2.getJobResume());
                                        dialog.showData(jobResumeService.getAllWithResumeId(id));
                                        frame.showMessage("Cập nhật trạng thái thành công");
                                        if(dialog2.getJobResume().getStatus() == Status.ACCEPTED){
                                            Invoice invoice = new ApplicationInvoice();
                                            Employee employee = ((ResumeFrameImp)frame).getEmpployee();
                                            Applicant applicant = dialog2.getJobResume().getResume().getApplicant();
                                            Job job = dialog2.getJobResume().getJob();

                                            ((ApplicationInvoice)invoice).setCreatedDate(LocalDate.now());
                                            ((ApplicationInvoice)invoice).setFee(countFee(dialog2.getJobResume().getJob()));
                                            ((ApplicationInvoice)invoice).setEmployee(employee);
                                            ((ApplicationInvoice)invoice).setApplicant(applicant);
                                            ((ApplicationInvoice)invoice).setJob(job);

                                            if(employee.getApplicationInvoices() == null){
                                                employee.setApplicationInvoices(new ArrayList<>());
                                            }
                                            employee.getApplicationInvoices().add((ApplicationInvoice)invoice);

                                            if(applicant.getApplicationInvoices() == null){
                                                applicant.setApplicationInvoices(new ArrayList<>());
                                            }
                                            applicant.getApplicationInvoices().add((ApplicationInvoice)invoice);

                                            if(job.getApplicationInvoices() == null){
                                                job.setApplicationInvoices(new ArrayList<>());
                                            }
                                            job.getApplicationInvoices().add((ApplicationInvoice)invoice);

                                            invoiceService.create(invoice);
                                        }
                                    } catch (RemoteException e) {
                                        frame.showMessage("Cập nhật trạng thái thất bại");
                                    }
                                }
                            }

                            @Override
                            public void onReset() {
                                dialog.showData(jobResumes);
                                dialog.getSearchStatusText().setSelectedIndex(0);
                                dialog.getSearchRecruiterText().setSelectedIndex(0);
                            }
                        });
                        dialog.visible();
                    } else {
                        frame.showMessage("Hồ sơ chưa ứng tuyển");
                    }
                } catch (RemoteException e) {
                    frame.showMessage("Kết nối bị lỗi");
                }
            }

            @Override
            public void onAdd() {

            }

            @Override
            public void onUpdate(int row) {
                int id = Integer.parseInt(frame.getTable().getModel().getValueAt(row, 0).toString());
                try {
                    Resume resume = resumeService.findById(id);
                    CreateUpdateDetailResumeDialog dialog = new CreateUpdateDetailResumeDialog(null, true,
                            resume.getApplicant(), ((ResumeFrameImp)frame).getEmpployee(), resume);
                    dialog.setVisible(true);
                    if(dialog.isConfirmed()){
                        resumeService.update(dialog.getResume());
                        show();
                        frame.showMessage("Cập nhật hồ sơ thành công");
                    }
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onDelete(int row) {
                int confirm = JOptionPane.showConfirmDialog(null, "Xóa hồ sơ này?");
                if (confirm == JOptionPane.YES_OPTION){
                    int id = Integer.parseInt(frame.getTable().getModel().getValueAt(row, 0).toString());
                    try {
                        Resume resume = resumeService.findById(id);

                        if(resume.getJobResumes()!=null && resume.getJobResumes().size()>0){
                            frame.showMessage("Hồ sơ đang được ứng tuyển");
                            return;
                        }

                        Applicant applicant = resume.getApplicant();
                        applicant.setResume(null);

                        resume.setApplicant(null);
                        resume.setEmployee(null);

                        List<Profession> professionList = new ArrayList<>(resume.getProfessions());
                        for (Profession p : professionList) {
                            p.getResumes().remove(resume);
                        }
                        resume.getProfessions().clear();

                        resumeService.delete(id);

                        show();
                        frame.showMessage("Xóa hồ sơ thành công");
                    } catch (RemoteException e) {
                        frame.showMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onExport(Object... object) {
                ExcelHelper excel=new ExcelHelper();
                excel.exportData((ResumeFrameImp) frame, frame.getTable(), 1);
            }

            @Override
            public void onReset() {

            }
        });
    }

    private double countFee(Job job){
        if(job.getSalary() < SALARY_GT5){
            return job.getSalary() * PERCENT_1 / 100;
        } else {
            if(job.getSalary() <= SALARY_GTE10){
                return job.getSalary() * PERCENT_2 / 100;
            } else {
                return job.getSalary() * PERCENT_3 / 100;
            }
        }
    }

    public void show(){
        try {
            frame.showData(resumeService.getAll());
        } catch (RemoteException e) {
            frame.showMessage(e.getMessage());
        }
    }
}
