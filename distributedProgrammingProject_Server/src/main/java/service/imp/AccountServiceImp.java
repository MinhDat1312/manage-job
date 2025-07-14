package service.imp;

import dao.AccountDAO;
import entity.Account;
import exception.checkUserEmail;
import exception.checkUserPass;
import service.inteface.AccountService;
import util.FilterImp;

import java.rmi.RemoteException;
import java.util.List;

public class AccountServiceImp extends GenericServiceImp<Account, Integer> implements AccountService {
    private AccountDAO accountDAO;

    public AccountServiceImp(AccountDAO dao) throws RemoteException {
        super(dao);
        this.accountDAO = dao;
    }

    @Override
    public Account login(String email, String password) throws checkUserEmail, checkUserPass {
        if (email.isEmpty() || password.isEmpty()) {
            throw new checkUserEmail("","Nhập đủ thông tin đăng nhập");
        }
        FilterImp filter = new FilterImp();
        if (!filter.checkUserEmail(email)) {
            throw new checkUserEmail(email,"Email không hợp lệ");
        }
        if (!filter.checkUserPass(password)) {
            throw new checkUserPass(password, "Mật khẩu không hợp lệ");
        }

        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);

        if(accountDAO.getAll().contains(account)){
            int index = accountDAO.getAll().indexOf(account);
            return accountDAO.getAll().get(index);
        } else {
            throw new checkUserEmail("", "Tài khoản không tồn tại");
        }
    }

    @Override
    public List<Account> getAllWithFilterName(String name) throws RemoteException {
        return accountDAO.getAllWithFilterName(name);
    }
}