package service.inteface;

import entity.Applicant;

import java.rmi.RemoteException;
import java.util.List;

public interface ApplicantService extends GenericService<Applicant, Integer> {
    List<Applicant> getAllWithInfo(int index, String info) throws RemoteException;
}