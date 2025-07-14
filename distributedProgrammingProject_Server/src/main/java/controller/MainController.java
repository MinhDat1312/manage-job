package controller;

import dao.*;
import entity.*;
import service.imp.*;
import view.frame.iframe.*;
import view.frame.imp.*;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    private final String HOME = "Trang chủ";
    private final String EMPLOYEE = "Nhân viên";
    private final String ACCOUNT = "Tài khoản";
    private final String APPLICANT = "Ứng viên";
    private final String RESUME = "Hồ sơ";
    private final String RECRUITER = "Nhà tuyển dụng";
    private final String JOB = "Tin tuyển dụng";
    private final String INVOICE = "Hóa đơn";
    private final String JOBSEARCH = "Tìm việc làm";
    private final String STATISTICS = "Thống kê";
    private final String LOGOUT = "Đăng xuất";

    private final IMainFrame frame;
    private Map<String, IFrame<?>> frameMap;
    private final Employee employee;
    private final String role;

    public MainController(IMainFrame frame, Employee employee, String role) {
        this.frame = frame;
        this.employee = employee;
        this.role = role;

        addFrame();
        frame.setUserInfo(employee, role);
        initializePanel();
        addMenuListener();

    }

    private void addFrame(){
        frameMap = new HashMap<String, IFrame<?>>();
        frameMap.put(HOME,new HomeFrameImp(employee));
		frameMap.put(EMPLOYEE, new EmployeeFrameImp(employee));
		frameMap.put(ACCOUNT, new AccountFrameImp(employee));
		frameMap.put(APPLICANT, new ApplicantFrameImp(employee));
		frameMap.put(RESUME, new ResumeFrameImp(employee));
		frameMap.put(RECRUITER, new RecruiterFrameImp(employee));
		frameMap.put(JOB, new JobFrameImp(employee));
		frameMap.put(INVOICE, new InvoiceFrameImp(employee));
		frameMap.put(JOBSEARCH, new JobSearchFrameImp(employee));
		frameMap.put(STATISTICS, new StatisticsFrameImp(employee));
    }

    private void initializePanel() {
        for (Map.Entry<String, IFrame<?>> entry : frameMap.entrySet()) {
            frame.addPanel(entry.getValue().getPanel(), entry.getKey());
        }
        frame.showPanel(HOME);
    }

    private void addMenuListener() {
        frame.setMenuListener( name -> {
            switch (name) {
                case HOME -> frame.showPanel(name);
                case EMPLOYEE -> {
                    new EmployeeController((IEmployeeFrame) frameMap.get(EMPLOYEE),
                            new EmployeeServiceImp(new EmployeeDAO(Employee.class)));
                    frame.showPanel(name);
                }
                case ACCOUNT -> {
                    new AccountController((IAccountFrame) frameMap.get(ACCOUNT),
                            new AccountServiceImp(new AccountDAO(Account.class)));
                    frame.showPanel(name);
                }
                case APPLICANT -> {
                    new ApplicantController((IApplicantFrame) frameMap.get(APPLICANT),
                            new ApplicantServiceImp(new ApplicantDAO(Applicant.class)));
                    frame.showPanel(name);
                }
                case RESUME -> {
                    new ResumeController((IResumeFrame) frameMap.get(RESUME),
                            new ResumeServiceImp(new ResumeDAO(Resume.class)));
                    frame.showPanel(name);
                }
                case RECRUITER -> {
                    new RecruiterController((IRecruiterFrame) frameMap.get(RECRUITER),
                            new RecruiterServiceImp(new RecruiterDAO(Recruiter.class)));
                    frame.showPanel(name);
                }
                case JOB -> {
                    new JobController((IJobFrame) frameMap.get(JOB),
                            new JobServiceImp(new JobDAO(Job.class)));
                    frame.showPanel(name);
                }
                case INVOICE -> {
                    new InvoiceController((IInvoiceFrame) frameMap.get(INVOICE),
                            new InvoiceServiceImp(new InvoiceDAO(Invoice.class)));
                    frame.showPanel(name);
                }
                case JOBSEARCH -> {
                    new JobSearchController((IJobSearchFrame) frameMap.get(JOBSEARCH),
                            new JobServiceImp(new JobDAO(Job.class)));
                    frame.showPanel(name);
                }
                case STATISTICS -> {
                    new StatisticsController((IStatisticsFrame) frameMap.get(STATISTICS));
                    frame.showPanel(name);
                }
                case LOGOUT -> {
                    int check = JOptionPane.showConfirmDialog(null, "Có chắc chắn đăng xuất?");
                    if (check == JOptionPane.OK_OPTION) {
                        frame.close();
                        ILoginFrame loginFrame = new LoginFrameImp();
                        LoginController loginController = new LoginController(loginFrame,
                                new AccountServiceImp(new AccountDAO(Account.class)));
                        loginController.show();
                    }
                }
            }
        });
    }

    public void show(){
        frame.visible();
    }
}
