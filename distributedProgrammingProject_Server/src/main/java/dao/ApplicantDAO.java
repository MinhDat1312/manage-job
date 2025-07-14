package dao;

import entity.Applicant;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ApplicantDAO extends GenericDAO<Applicant, Integer> {

    public ApplicantDAO(Class<Applicant> clazz) {
        super(clazz);
    }

    public ApplicantDAO(Class<Applicant> clazz, EntityManager em) {
        super(clazz, em);
    }

    public List<Applicant> getAllWithInfo(int index, String info) {
        if(index == 0){
            return getAllWithFilter("a.name LIKE ?1", "%"+info+"%");
        } else if(index == 1){
            return getAllWithFilter("a.contact.phone LIKE ?1", "%"+info+"%");
        } else {
            return getAllWithFilter("a.contact.email LIKE ?1", "%"+info+"%");
        }
    }

}
