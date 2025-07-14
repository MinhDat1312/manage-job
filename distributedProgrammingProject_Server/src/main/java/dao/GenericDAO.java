package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.Getter;
import util.JPAUtil;

import java.util.List;
import java.util.function.Consumer;

public abstract class GenericDAO<T, ID> {
    protected EntityManager em;
    protected Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
        em = JPAUtil.getEntityManager();
    }

    public GenericDAO(Class<T> clazz, EntityManager em) {
        this.clazz = clazz;
        this.em = em;
    }

    public boolean create(T t) {
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            em.persist(t);
            et.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();

            return false;
        }
    }

    public boolean update(T t) {
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            em.merge(t);
            et.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();

            return false;
        }
    }

    public boolean delete(ID id) {
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            T t = em.find(clazz, id);
            if(t != null) {
                em.remove(t);
                et.commit();

                return true;
            } else {
                et.rollback();

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();

            return false;
        }
    }

    public T findById(ID id){
        return em.find(clazz, id);
    }

    public List<T> getAll() {
        return em.createQuery("FROM " + clazz.getSimpleName(), clazz)
                .getResultList();
    }

    public EntityManager getEntityManager() {return em;}

    public List<T> getAllWithFilter(String whereQuery, Object... search) {
        String sql = "FROM " + clazz.getSimpleName() + " a";

        if (whereQuery != null && !whereQuery.trim().isEmpty()) {
            sql += " WHERE " + whereQuery;
        }

        TypedQuery<T> query = em.createQuery(sql, clazz);

        for (int i = 0; i < search.length; i++) {
            query.setParameter(i + 1, search[i]);
        }

        return query.getResultList();
    }

    public List<T> getAllWithJoinFilter(String joinQuery, String whereQuery, Object... search) {
        String sql = "FROM " + clazz.getSimpleName() + " a JOIN " + joinQuery;

        if (whereQuery != null && !whereQuery.trim().isEmpty()) {
            sql += " WHERE " + whereQuery;
        }

        TypedQuery<T> query = em.createQuery(sql, clazz);

        for (int i = 0; i < search.length; i++) {
            query.setParameter(i + 1, search[i]);
        }

        return query.getResultList();
    }

    public List<T> getAllCustom(String sql, Object... search) {
        TypedQuery<T> query = em.createQuery(sql, clazz);

        for (int i = 0; i < search.length; i++) {
            query.setParameter(i + 1, search[i]);
        }

        return query.getResultList();
    }

    @SafeVarargs
    public final boolean deleteWithRelations(ID id, Consumer<T>... relationHandlers) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entity = em.find(clazz, id);
            if (entity == null) {
                tx.rollback();
                return false;
            }
            for (Consumer<T> h : relationHandlers) {
                h.accept(entity);
            }
            em.remove(entity);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }

}
