package service.imp;

import dao.GenericDAO;
import jakarta.persistence.EntityManager;
import service.inteface.GenericService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.function.Consumer;

public class GenericServiceImp<T, ID> extends UnicastRemoteObject implements GenericService<T, ID> {
    private GenericDAO<T, ID> dao;

    public GenericServiceImp(GenericDAO<T, ID> dao) throws RemoteException {
        this.dao = dao;
    }

    @Override
    public boolean create(T t) throws RemoteException {
        return dao.create(t);
    }

    @Override
    public boolean update(T t) throws RemoteException {
        return dao.update(t);
    }

    @Override
    public boolean delete(ID id) throws RemoteException {
        return dao.delete(id);
    }

    @Override
    public T findById(ID id) throws RemoteException {
        return dao.findById(id);
    }

    @Override
    public List<T> getAll() throws RemoteException {
        return dao.getAll();
    }

    @Override
    public EntityManager getEntityManager() throws RemoteException {
        return dao.getEntityManager();
    }

    @Override
    public List<T> getAllWithFilter(String whereQuery, Object... search) throws RemoteException {
        return dao.getAllWithFilter(whereQuery, search);
    }

    @Override
    public List<T> getAllWithJoinFilter(String joinQuery, String whereQuery, Object... search) throws RemoteException {
        return dao.getAllWithJoinFilter(joinQuery, whereQuery, search);
    }

    @Override
    public List<T> getAllCustom(String sql, Object... search) throws RemoteException {
        return dao.getAllCustom(sql, search);
    }

    @Override
    public boolean deleteWithRelations(ID id, Consumer<T>... relationHandlers) throws RemoteException {
        return dao.deleteWithRelations(id, relationHandlers);
    }
}
