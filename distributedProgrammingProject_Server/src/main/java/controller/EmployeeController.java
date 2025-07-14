package controller;

import dao.AccountDAO;
import entity.Account;
import entity.Employee;
import service.imp.AccountServiceImp;
import service.inteface.AccountService;
import service.inteface.EmployeeService;
import util.ExcelHelper;
import view.dialog.createupdate.CreateUpdateAccountDialog;
import view.dialog.createupdate.CreateUpdateEmployeeDialog;
import view.frame.iframe.IEmployeeFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.imp.EmployeeFrameImp;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.List;

public class EmployeeController {
    private final IEmployeeFrame frame;
    private final EmployeeService service;
    private AccountService accountService;

    public EmployeeController(IEmployeeFrame frame, EmployeeService service) {
        this.frame = frame;
        this.service = service;
        try {
            this.accountService = new AccountServiceImp(new AccountDAO(Account.class));
        } catch (RemoteException e) {
            frame.showMessage("Kết nối bị lỗi");
        }

        show();
        setListener();
        setEmployeeListener();
    }

    private void setListener() {
        frame.setFrameListener(new IFrameListener() {
            @Override
            public void onSearch(Object criteria) {
                Object[] objects = (Object[]) criteria;
                int index = Integer.parseInt(objects[0].toString());
                String searchText = frame.getSearchText();

                try {
                    List<Employee> data = service.getAllWithFilterOption(index, searchText);
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
                CreateUpdateEmployeeDialog dialog = new CreateUpdateEmployeeDialog(null, true);
                dialog.setVisible(true);
                if(dialog.isConfirmed()){
                    try {
                        service.create(dialog.getEmployee());
                        show();
                        frame.showMessage("Thêm nhân viên thành công");
                    } catch (RemoteException e) {
                        frame.showMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onUpdate(int row) {
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    Employee emp = service.findById(id);
                    if(emp != null){
                        CreateUpdateEmployeeDialog dialog = new CreateUpdateEmployeeDialog(null, true, emp);
                        dialog.setVisible(true);
                        if(dialog.isConfirmed()){
                            service.update(emp);
                            show();
                            frame.showMessage("Cập nhật nhân viên thành công");
                        }
                    }
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onDelete(int row) {
                int confirm = JOptionPane.showConfirmDialog(null, "Xóa nhân viên này?");
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                    try {
                        Employee emp = service.findById(id);
                        if((emp.getApplicationInvoices() != null && emp.getApplicationInvoices().size() > 0)
                            || (emp.getPostedInvoices()!=null && emp.getPostedInvoices().size() > 0)){
                            frame.showMessage("Không thể xóa nhân viên này vì có thông tin hóa đơn");
                            return;
                        }
                        if(emp.getResumes()!=null && emp.getResumes().size() > 0){
                            frame.showMessage("Không thể xóa nhân viên này vì có thông tin hồ sơ ứng viên");
                        }
                        service.delete(id);
                        show();
                        frame.showMessage("Xóa nhân viên thành công");
                    } catch (Exception e) {
                        frame.showMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onExport(Object... object) {
                ExcelHelper excel = new ExcelHelper();
			    excel.exportData((EmployeeFrameImp)frame, frame.getTable(), 2);
            }

            @Override
            public void onReset() {

            }
        });
    }

    private void setEmployeeListener() {
        frame.setEmployeeListener(row -> {
            int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
            try {
                Employee emp = service.findById(id);
                if(emp != null && emp.getAccount() == null){
                    CreateUpdateAccountDialog dialog = new CreateUpdateAccountDialog(null, true, emp);
                    dialog.setVisible(true);
                    if(dialog.isConfirmed()){
                        accountService.create(dialog.getAccount());
                        show();
                        frame.showMessage("Cấp tài khoản thành công");
                    }
                } else {
                    frame.showMessage("Nhân viên đã có tài khoản");
                }
            } catch (RemoteException e) {
                frame.showMessage(e.getMessage());
            }
        });
    }

    private void show() {
        try {
            frame.showData(service.getAll());
        } catch (RemoteException e) {
            frame.showMessage(e.getMessage());
        }
    }
}
