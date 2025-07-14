package controller;

import dao.EmployeeDAO;
import entity.Account;
import entity.Employee;
import service.imp.EmployeeServiceImp;
import service.inteface.AccountService;
import service.inteface.EmployeeService;
import util.ExcelHelper;
import view.dialog.createupdate.CreateUpdateAccountDialog;
import view.frame.iframe.IAccountFrame;
import view.frame.iframe.ilistener.IFrameListener;
import view.frame.imp.AccountFrameImp;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.List;

public class AccountController {
    private final IAccountFrame frame;
    private final AccountService accountService;
    private EmployeeService employeeService;

    public AccountController(IAccountFrame frame, AccountService accountService) {
        this.frame = frame;
        this.accountService = accountService;
        try {
            this.employeeService = new EmployeeServiceImp(new EmployeeDAO(Employee.class));
        } catch (RemoteException e) {
            frame.showMessage("Kết nối bị lỗi");
        }

        show();
        setListener();
    }

    private void setListener() {
        frame.setFrameListener(new IFrameListener<Account>() {
            @Override
            public void onSearch(Object criteria) {
                Object[] obj = (Object[]) criteria;
                int index = Integer.parseInt(obj[0].toString());
                String name = obj[1].toString();
                try {
                    List<Account> accounts = accountService.getAllWithFilterName(name);
                    frame.showData(accounts);
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
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
                int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                try {
                    Account account = accountService.findById(id);
                    if(account != null){
                        CreateUpdateAccountDialog dialog = new CreateUpdateAccountDialog(
                                null, true, account.getEmployee(), account
                        );
                        dialog.setVisible(true);
                        if(dialog.isConfirmed()){
                            accountService.update(account);
                            show();
                            frame.showMessage("Cập nhật tài khoản thành công");
                        }
                    }
                } catch (RemoteException e) {
                    frame.showMessage(e.getMessage());
                }
            }

            @Override
            public void onDelete(int row) {
                int confirm = JOptionPane.showConfirmDialog(null, "Xóa tài khoản này?");
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(frame.getTable().getValueAt(row, 0).toString());
                    try {
                        Employee emp = accountService.findById(id).getEmployee();
                        emp.setAccount(null);
                        employeeService.update(emp);
                        accountService.delete(id);
                        show();
                        frame.showMessage("Xóa tài khoản thành công");
                    } catch (Exception e) {
                        frame.showMessage(e.getMessage());
                    }
                }
            }

            @Override
            public void onExport(Object... object) {
                ExcelHelper excel = new ExcelHelper();
			    excel.exportData((AccountFrameImp)frame, frame.getTable(), 1);
            }

            @Override
            public void onReset() {

            }
        });
    }

    public void show(){
        try {
            frame.showData(accountService.getAll());
        } catch (RemoteException e) {
            frame.showMessage(e.getMessage());
        }
    }
}
