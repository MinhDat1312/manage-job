package dao;

import entity.Profession;
import jakarta.persistence.EntityManager;

public class ProfessionDAO extends GenericDAO<Profession, Integer>{

    public ProfessionDAO(Class<Profession> clazz) {
        super(clazz);
    }

    public ProfessionDAO(Class<Profession> clazz, EntityManager em) {
        super(clazz, em);
    }
}
