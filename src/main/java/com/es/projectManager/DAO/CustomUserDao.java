package com.es.projectManager.DAO;

import com.es.projectManager.exception.ProductDefinitionException;
import com.es.projectManager.model.User;
import com.es.projectManager.model.UserType;
import com.es.projectManager.sessionFactory.SessionFactory;
import com.es.projectManager.util.SyncObjectPool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CustomUserDao implements UserDao{
    private static final String USER_SESSION_ATTRIBUTE = "user";
    private static CustomUserDao customUserDao;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();


    private CustomUserDao() {
        //saveSampleProducts();
    }

    public static synchronized CustomUserDao getInstance() {
        if (customUserDao == null) {
            customUserDao = new CustomUserDao();
        }
        return customUserDao;
    }
    @Override
    public Optional<User> read(Integer id) {
        readLock.lock();
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("user_id"), id));
            return Optional.ofNullable(session.createQuery(cr).getSingleResult());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return Optional.empty();
        }finally {
            readLock.unlock();
        }
    }
    @Override
    public void saveOrUpdate(User user) {
        if (user == null)
            throw new ProductDefinitionException("User has no data");
        writeLock.lock();
        try {
            try(Session session= SessionFactory.getSessionFactory().openSession()){
                Transaction tx=session.beginTransaction();
                if (user.getUser_id() == null) {
                    session.persist(user);
                } else {
                    session.merge(user);
                }
                tx.commit();
            }catch (PersistenceException exception){
                exception.printStackTrace();
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<User> readAll() {
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM User", User.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    @Override
    public Optional<User> readByLogin(String login){
        readLock.lock();
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("login"), login));
            return Optional.ofNullable(session.createQuery(cr).getSingleResult());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return Optional.empty();
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object syncObject = SyncObjectPool.getSyncObject(session.getId());
        synchronized (syncObject) {
            return (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
        }
    }

    @Override
    public void setUser(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object syncObject = SyncObjectPool.getSyncObject(session.getId());
        synchronized (syncObject) {
            session.setAttribute(USER_SESSION_ATTRIBUTE, user);
        }
    }

    @Override
    public List<User> readEmployees() {
        List<User> users =readAll();
        List<User> employees = new ArrayList<>();
        for (User user: users) {
            if(user.getType().equals(UserType.EMPLOYEE)){
                employees.add(user);
            }
        }
        return employees;
    }

}
