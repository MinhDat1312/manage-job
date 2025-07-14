package controller;

import dao.JobDAO;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import service.imp.JobServiceImp;
import service.inteface.JobService;
import service.inteface.RecruiterService;
import util.ExcelHelper;
import view.dialog.createupdate.CreateUpdateDetailJobDialog;
import view.dialog.createupdate.CreateUpdateRecruiterDialog;
import view.dialog.idialog.IDialogListener;
import view.dialog.list.ListJobsDialog;
import view.frame.iframe.IRecruiterFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.iframe.ilistener.IRecuiterListener;
import view.frame.imp.RecruiterFrameImp;

import javax.swing.*;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecruiterController {

    private final IRecruiterFrame frame;
    private final RecruiterService recruiterService;
    private JobService jobService;

    public RecruiterController(IRecruiterFrame frame, RecruiterService recruiterService) {
        this.frame = frame;
        this.recruiterService = recruiterService;
        try {
            this.jobService = new JobServiceImp(new JobDAO(Job.class));
        } catch (RemoteException e) {
            frame.showMessage("Kết nối bị lỗi");
        }

        show();
        setListener();
        setRecuiterListener();
    }

    private void setListener() {
        frame.setFrameListener(new IFrameListener() {

            @Override
            public void onSearch(Object criteria) {
                Object[] objects= (Object[]) criteria;
                int index = (int) objects[0];
                String searchText = (String) objects[1];

                try {
                    List<Recruiter> data = recruiterService.getAllWithFilterOption(index, searchText);
                    frame.showData(data);
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onView(int row) {

            }

            @Override
            public void onAdd() {
                CreateUpdateRecruiterDialog dialog = new CreateUpdateRecruiterDialog(null, true);
                dialog.setVisible(true);
                if(dialog.isConfirmed()){
                    try {
                        recruiterService.create(dialog.getRecruiter());
                        show();
                        frame.showMessage("Thêm nhà tuyển dụng thành công");
                    } catch (RemoteException e) {
                        frame.showMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onUpdate(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    Recruiter recruiter = recruiterService.findById(id);
                    if(recruiter!=null){
                        CreateUpdateRecruiterDialog dialog = new CreateUpdateRecruiterDialog(null, true,
                               recruiter);
                        dialog.setVisible(true);
                        if(dialog.isConfirmed()) {
                            recruiterService.update(dialog.getRecruiter());
                            show();
                            frame.showMessage("Cập nhật nhà tuyển dụng thành công");
                        }
                    }
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onDelete(int row) {
                int confirm = JOptionPane.showConfirmDialog(null, "Xóa nhà tuyển dụng này?");
                if (confirm == JOptionPane.YES_OPTION){
                    int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                    try {
                        Recruiter recruiter = recruiterService.findById(id);
                        if(recruiter.getPostedInvoices()!=null && recruiter.getPostedInvoices().size()>0){
                            frame.showMessage("Không thể xóa nhà tuyển dụng này vì có thông tin hóa đơn");
                            return;
                        }

                        if(recruiter.getJobs()!=null){
                            for(Job job : recruiter.getJobs()){
                                if((job.getApplicationInvoices()!=null && job.getApplicationInvoices().size()>0)
                                    || job.getPostedInvoice()!=null){
                                    frame.showMessage("Tin tuyển dụng của nhà tuyển dụng này có thông tin hóa đơn");
                                    return;
                                }
                            }
                            recruiterService.deleteWithRelations(
                                    id,
                                    r -> {
                                        List<Job> jobs = new ArrayList<>(r.getJobs());
                                        for(Job job:jobs){
                                            List<Profession> professionList = new ArrayList<>(job.getProfessions());
                                            for (Profession p : professionList) {
                                                p.getJobs().remove(job);
                                            }
                                            job.getProfessions().clear();
                                            r.getJobs().remove(job);
                                            job.setRecruiter(null);

                                            try {
                                                EntityManager em = recruiterService.getEntityManager();
                                                em.remove(em.contains(job) ? job : em.merge(job));
                                            } catch (RemoteException e) {
                                                frame.showMessage("Kết nối bị lỗi");
                                            }
                                        }
                                    }
                            );
                        } else {
                            recruiterService.delete(id);
                        }
                        show();
                        frame.showMessage("Xóa nhà tuyển dụng thành công");
                    } catch (RemoteException e) {
                        frame.showMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onExport(Object... object) {
                ExcelHelper excel = new ExcelHelper();
                excel.exportData((RecruiterFrameImp) frame, frame.getTable(), 2);
            }

            @Override
            public void onReset() {

            }
        });
    }

    private void setRecuiterListener() {
        frame.setRecuiterListener(new IRecuiterListener() {
            @Override
            public void onViewJobs(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    Recruiter recruiter = recruiterService.findById(id);
                    List<Job> jobs = recruiter.getJobs() != null ? recruiter.getJobs() : new ArrayList<Job>();
                    ListJobsDialog dialog = new ListJobsDialog(null, true, recruiter);
                    dialog.showData(jobs);
                    dialog.setDialogListener(new IDialogListener<Job>() {

                        @Override
                        public void onSearch(Object criteria) {
                            Object[] objects= (Object[]) criteria;
                            String searchTitle = (String) objects[0];
                            String searchStatus = (String) objects[1];

                            boolean hasTitle = !searchTitle.isEmpty()
                                    && !searchTitle.equalsIgnoreCase("Nhập dữ liệu");
                            boolean hasStatus = !searchStatus.equalsIgnoreCase("Trạng thái");

                            try{
                                List<Job> data = null;
                                if(!hasStatus && hasTitle){
                                    data = jobService.getJobsWithFilterOption(1, searchTitle, id);
                                } else if(hasStatus && !hasTitle){
                                    data = jobService.getJobsWithFilterOption(2,
                                            searchStatus.equalsIgnoreCase("Khả dụng"), id);
                                } else if(hasStatus && hasTitle) {
                                    data = jobService.getJobsWithFilterOption(3,
                                            searchStatus.equalsIgnoreCase("Khả dụng"),
                                            searchTitle, id
                                            );
                                } else {
                                    dialog.showMessage("Chọn tiêu chí để tìm kiếm");
                                    return;
                                }
                                dialog.showData(data);
                            } catch (RemoteException e) {
                                frame.showMessage(e.getMessage());
                            }
                        }

                        @Override
                        public void onView(int row) {
                            try {
                                Job job = jobService.findById(
                                        Integer.parseInt(dialog.getTable().getValueAt(row, 0).toString())
                                );
                                CreateUpdateDetailJobDialog dialog1 = new CreateUpdateDetailJobDialog(
                                        null, true, recruiter, job, true
                                );
                                dialog1.setVisible(true);
                            } catch (RemoteException e) {
                                dialog.showMessage(e.getMessage());
                            }
                        }

                        @Override
                        public void onReset() {
                            dialog.addPlaceHolder(dialog.getSearchTitleText());
                            dialog.getSearchStatusText().setSelectedIndex(0);
                            dialog.showData(jobs);
                        }
                    });
                    dialog.visible();
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onCreateJob(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    Recruiter recruiter = recruiterService.findById(id);
                    CreateUpdateDetailJobDialog dialog = new CreateUpdateDetailJobDialog(null, true, recruiter);
                    dialog.setVisible(true);
                    if(dialog.isConfirmed()){
                        if(recruiter.getJobs() != null){
                            recruiter.getJobs().add(dialog.getJob());
                        } else {
                            List<Job> jobs = new ArrayList<>();
                            jobs.add(dialog.getJob());
                            recruiter.setJobs(jobs);
                        }
                        jobService.create(dialog.getJob());
                        show();
                        frame.showMessage("Tạo tin tuyển dụng thành công");
                    }
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }
        });
    }

    private void show(){
        try {
            frame.showData(recruiterService.getAll());
        } catch (RemoteException e) {
            frame.showMessage(e.getMessage());
        }
    }
}
