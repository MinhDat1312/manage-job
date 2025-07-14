package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "default";
    private static EntityManagerFactory em;

    static {
        try {
            em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EntityManager getEntityManager() {
        return em.createEntityManager();
    }

    public static void closeEntityManager() {
        if(em != null && em.isOpen()) {
            em.close();
        }
    }
}
