package dao;

import entity.Account;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AccountDAO extends GenericDAO<Account, Integer> {

    public AccountDAO(Class<Account> clazz) {
        super(clazz);
    }

    public AccountDAO(Class<Account> clazz, EntityManager em) {
        super(clazz, em);
    }

    public List<Account> getAllWithFilterName(String name){
        return getAllWithFilter(
                "a.employee.name LIKE ?1",
                "%" + name + "%"
        );
    }
}
