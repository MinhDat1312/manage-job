package dao;

import entity.Recruiter;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RecruiterDAO extends GenericDAO<Recruiter, Integer> {

    public RecruiterDAO(Class<Recruiter> clazz) {
        super(clazz);
    }

    public RecruiterDAO(Class<Recruiter> clazz, EntityManager em) {
        super(clazz, em);
    }

    public List<Recruiter> getAllWithFilterOption(int option, String searchText){
        if(option == 0){
            return getAllWithFilter("a.name LIKE ?1", "%" + searchText + "%");
        } else {
            return getAllWithFilter("a.contact.phone LIKE ?1", "%" + searchText + "%");
        }
    }
}
