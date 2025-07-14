package service.imp;

import dao.EmployeeDAO;
import entity.Employee;
import entity.constant.Gender;
import service.inteface.EmployeeService;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImp extends GenericServiceImp<Employee, Integer> implements EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeServiceImp(EmployeeDAO dao) throws RemoteException {
        super(dao);
        this.employeeDAO = dao;
    }

    @Override
    public Map<Gender, Long> genderStatistics() throws RemoteException {
        return employeeDAO.genderStatistics();
    }

    @Override
    public List<Employee> getAllWithFilterOption(int option, String searchText) throws RemoteException {
        return employeeDAO.getAllWithFilterOption(option, searchText);
    }
}