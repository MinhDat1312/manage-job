package service.imp;

import dao.ApplicantDAO;
import entity.Applicant;
import service.inteface.ApplicantService;

import java.rmi.RemoteException;
import java.util.List;

public class ApplicantServiceImp extends GenericServiceImp<Applicant, Integer> implements ApplicantService {
    private ApplicantDAO applicantDAO;

    public ApplicantServiceImp(ApplicantDAO dao) throws RemoteException {
        super(dao);
        this.applicantDAO = dao;
    }

    @Override
    public List<Applicant> getAllWithInfo(int index, String info) throws RemoteException {
        return applicantDAO.getAllWithInfo(index, info);
    }
}