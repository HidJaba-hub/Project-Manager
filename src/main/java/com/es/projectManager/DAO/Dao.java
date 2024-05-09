package com.es.projectManager.DAO;

import com.es.projectManager.sessionFactory.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public interface Dao <T>{

    List<T> readAll();
    void saveOrUpdate(T entity);
    default Optional<T> read(Integer obj){return Optional.empty();}

    default boolean delete(T entity) {
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
