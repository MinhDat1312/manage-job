package service.imp;

import dao.ProfessionDAO;
import entity.Profession;
import service.inteface.ProfessionService;

import java.rmi.RemoteException;

public class ProfessionServiceImp extends GenericServiceImp<Profession, Integer> implements ProfessionService {
    private ProfessionDAO professionDAO;

    public ProfessionServiceImp(ProfessionDAO dao) throws RemoteException {
        super(dao);
        this.professionDAO = dao;
    }
}