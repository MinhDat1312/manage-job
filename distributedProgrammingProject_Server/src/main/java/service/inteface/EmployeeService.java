package service.inteface;

import entity.Employee;
import entity.constant.Gender;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface EmployeeService extends GenericService<Employee, Integer> {
    Map<Gender, Long> genderStatistics() throws RemoteException;
    List<Employee> getAllWithFilterOption(int option, String searchText) throws RemoteException;
}