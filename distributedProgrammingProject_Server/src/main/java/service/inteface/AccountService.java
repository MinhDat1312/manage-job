package service.inteface;

import entity.Account;
import exception.checkUserEmail;
import exception.checkUserPass;

import java.rmi.RemoteException;
import java.util.List;

public interface AccountService extends GenericService<Account, Integer> {
    Account login(String email, String password)
            throws RemoteException, checkUserEmail, checkUserPass;

    List<Account> getAllWithFilterName(String name) throws RemoteException;
}