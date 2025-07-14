package service.imp;

import dao.RecruiterDAO;
import entity.Recruiter;
import jakarta.persistence.EntityManager;
import service.inteface.RecruiterService;

import java.rmi.RemoteException;
import java.util.List;

public class RecruiterServiceImp extends GenericServiceImp<Recruiter, Integer> implements RecruiterService {
    private RecruiterDAO recruiterDAO;

    public RecruiterServiceImp(RecruiterDAO dao) throws RemoteException {
        super(dao);
        this.recruiterDAO = dao;
    }

    @Override
    public List<Recruiter> getAllWithFilterOption(int option, String searchText) throws RemoteException {
        return recruiterDAO.getAllWithFilterOption(option, searchText);
    }
}