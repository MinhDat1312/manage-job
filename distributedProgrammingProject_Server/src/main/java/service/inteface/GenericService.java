package service.inteface;

import jakarta.persistence.EntityManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.function.Consumer;

public interface GenericService<T, ID> extends Remote {
    boolean create(T t) throws RemoteException;

    boolean update(T t) throws RemoteException;

    boolean delete(ID id) throws RemoteException;

    T findById (ID id) throws RemoteException;

    List<T> getAll() throws RemoteException;

    EntityManager getEntityManager() throws RemoteException;

    List<T> getAllWithFilter(String whereQuery, Object... search) throws RemoteException;

    List<T> getAllWithJoinFilter(String joinQuery, String whereQuery, Object... search) throws RemoteException;

    List<T> getAllCustom(String sql, Object... search) throws RemoteException;

    boolean deleteWithRelations(ID id, Consumer<T>... relationHandlers) throws RemoteException;
}
