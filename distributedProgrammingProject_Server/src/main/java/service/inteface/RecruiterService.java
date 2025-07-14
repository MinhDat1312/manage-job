package service.inteface;

import entity.Recruiter;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.util.List;

public interface RecruiterService extends GenericService<Recruiter, Integer> {
    List<Recruiter> getAllWithFilterOption(int option, String searchText) throws RemoteException;
}