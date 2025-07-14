package controller;

import dao.JobResumeDAO;
import dao.RecruiterDAO;
import dao.ResumeDAO;
import entity.*;
import entity.constant.Status;
import service.imp.JobResumeServiceImp;
import service.imp.RecruiterServiceImp;
import service.imp.ResumeServiceImp;
import service.inteface.*;
import util.ExcelHelper;
import view.dialog.createupdate.CreateUpdateApplicantDialog;
import view.dialog.createupdate.CreateUpdateDetailResumeDialog;
import view.dialog.idialog.IDialogListener;
import view.dialog.list.ListResumesDialog;
import view.frame.iframe.IApplicantFrame;
import view.frame.iframe.ilistener.IApplicantListener;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.imp.ApplicantFrameImp;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ApplicantController{

    private final IApplicantFrame frame;
    private final ApplicantService service;
    private ResumeService resumeService;
    private RecruiterService recruiterService;
    private JobResumeService jobResumeService;

    public ApplicantController(IApplicantFrame frame, ApplicantService service) {
        this.frame = frame;
        this.service = service;

        try {
            resumeService = new ResumeServiceImp(new ResumeDAO(Resume.class));
            recruiterService = new RecruiterServiceImp(new RecruiterDAO(Recruiter.class));
            this.jobResumeService = new JobResumeServiceImp(new JobResumeDAO(JobResume.class));
        } catch (RemoteException e) {
            frame.showMessage("Kết nối bị lỗi");
        }

        show();
        setListener();
        setApplicantListener();
    }

    private void setListener() {
        frame.setFrameListener(new IFrameListener() {

            @Override
            public void onSearch(Object criteria) {
                Object[] objects = (Object[]) criteria;
                int index = Integer.parseInt(objects[0].toString());
                String searchText = frame.getSearchText();

                try {
                    List<Applicant> data = service.getAllWithInfo(index, "%" + searchText + "%");
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
                CreateUpdateApplicantDialog dialog = new CreateUpdateApplicantDialog(null, true);
                dialog.setVisible(true);
                if(dialog.isConfirmed()){
                    try {
                        service.create(dialog.getApplicant());
                        show();
                        frame.showMessage("Thêm ứng viên thành công");
                    } catch (RemoteException e){
                        frame.showMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onUpdate(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    Applicant app = service.findById(id);
                    if(app!=null){
                        CreateUpdateApplicantDialog dialog = new CreateUpdateApplicantDialog(null, true, app);
                        dialog.setVisible(true);
                        if(dialog.isConfirmed()){
                            service.update(app);
                            show();
                            frame.showMessage("Cập nhật ứng viên thành công");
                        }
                    }
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onDelete(int row) {
                int confirm = JOptionPane.showConfirmDialog(null, "Xóa ứng viên này?");
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                    try {
                        Applicant app = service.findById(id);
                        if(app.getApplicationInvoices()!= null && app.getApplicationInvoices().size()>0){
                            frame.showMessage("Không thể xóa ứng viên này vì có thông tin hóa đơn");
                            return;
                        }
                        Resume resume = app.getResume();
                        if (resume != null) {
                            if(resume.getJobResumes()!=null && resume.getJobResumes().size()>0){
                                frame.showMessage("Ứng viên đang có hồ sơ ứng tuyển");
                                return;
                            }
                            service.deleteWithRelations(
                                id,
                                a -> {
                                    Resume r = a.getResume();

                                    a.setResume(null);
                                    r.setEmployee(null);
                                    r.setApplicant(null);

                                    List<Profession> professionList = new ArrayList<>(r.getProfessions());
                                    for (Profession p : professionList) {
                                        p.getResumes().remove(r);
                                    }
                                    r.getProfessions().clear();

                                    try {
                                        resumeService.update(r);
                                        resumeService.delete(r.getId());
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            );
                        } else {
                            service.delete(id);
                        }
                        show();
                        frame.showMessage("Xóa ứng viên thành công");
                    } catch (Exception e) {
                        e.printStackTrace();
                        frame.showMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onExport(Object... object) {
                ExcelHelper excel = new ExcelHelper();
                excel.exportData((ApplicantFrameImp)frame, frame.getTable(), 2);
            }

            @Override
            public void onReset() {

            }
        });
    }

    private void setApplicantListener(){
        frame.setApplicantListener(new IApplicantListener() {

            @Override
            public void onCreateResume(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try{
                    Applicant app = service.findById(id);
                    Employee emp = ((ApplicantFrameImp)frame).getEmployee();
                    if(app!=null && app.getResume() == null){
                        CreateUpdateDetailResumeDialog dialog = new CreateUpdateDetailResumeDialog(null, true, app, emp);
                        dialog.setVisible(true);
                        if(dialog.isConfirmed()){
                            resumeService.create(dialog.getResume());
                            show();
                            frame.showMessage("Tạo hồ sơ thành công");
                        }
                    } else {
                        frame.showMessage("Ứng viên đã tạo hồ sơ");
                    }
                } catch (RemoteException e){
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onViewResume(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    Applicant app = service.findById(id);
                    Resume resume = app.getResume() != null ? app.getResume() : null;
                    if(resume == null){
                        frame.showMessage("Ứng viên chưa tạo hồ sơ");
                        return;
                    }
                    if(resume.getJobResumes().size() == 0){
                        frame.showMessage("Ứng viên chưa ứng tuyển");
                        return;
                    }
                    ListResumesDialog dialog = new ListResumesDialog(null, true, app);
                    dialog.loadDataRecruiter(recruiterService.getAll());
                    dialog.showData(resume.getJobResumes());
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
                            try {
                                String jobTitle = dialog.getTable().getValueAt(row, 3).toString();
                                JobResume jobResume = null;
                                for (JobResume j: jobResumeService.getAllWithResumeId(resume.getId())){
                                    if(j.getJob().getTitle().equals(jobTitle)){
                                        jobResume = j;
                                        break;
                                    }
                                }
                                CreateUpdateDetailResumeDialog dialog2 = new CreateUpdateDetailResumeDialog(null, true,
                                        resume, app, jobResume);
                                dialog2.setVisible(true);
                            } catch (RemoteException e) {
                                dialog.showMessage(e.getMessage());
                            }
                        }

                        @Override
                        public void onReset() {
                            dialog.showData(resume.getJobResumes(), resume);
                            dialog.getSearchStatusText().setSelectedIndex(0);
                            dialog.getSearchRecruiterText().setSelectedIndex(0);
                        }
                    });
                    dialog.visible();
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }
        });
    }

    private void show(){
        try {
            frame.showData(service.getAll());
        } catch (RemoteException e) {
            frame.showMessage(e.getMessage());
        }
    }
}