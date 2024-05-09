package com.es.projectManager.DAO;

import com.es.projectManager.exception.ProductDefinitionException;
import com.es.projectManager.model.Project;
import com.es.projectManager.model.User;
import com.es.projectManager.sessionFactory.SessionFactory;
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

public class CustomProjectDao implements ProjectDao{

    private static CustomProjectDao customProjectDao;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();


    private CustomProjectDao() {
        //saveSampleProducts();
    }

    public static synchronized CustomProjectDao getInstance() {
        if (customProjectDao == null) {
            customProjectDao = new CustomProjectDao();
        }
        return customProjectDao;
    }

    @Override
    public List<Project> readAll() {
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Project", Project.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveOrUpdate(Project project) {
        if (project == null)
            throw new ProductDefinitionException("User has no data");
        writeLock.lock();
        try {
            try(Session session= SessionFactory.getSessionFactory().openSession()){
                Transaction tx=session.beginTransaction();
                if (project.getProject_id() == null) {
                    session.persist(project);
                } else {
                    session.merge(project);
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
    public Optional<Project> read(Integer id) {
        readLock.lock();
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Project> cr = cb.createQuery(Project.class);
            Root<Project> root = cr.from(Project.class);
            cr.select(root).where(cb.equal(root.get("project_id"), id));
            return Optional.ofNullable(session.createQuery(cr).getSingleResult());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return Optional.empty();
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Project> readByUser(User user) {
        List<Project> allProjects = readAll();
        List<Project> userProjects = new ArrayList<>();
        for (Project project: allProjects) {
            if(project.getUsers().contains(user)){
                userProjects.add(project);
            }
        }
        return userProjects;
    }

    @Override
    public Optional<Project> readByName(String name) {
        readLock.lock();
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Project> cr = cb.createQuery(Project.class);
            Root<Project> root = cr.from(Project.class);
            cr.select(root).where(cb.equal(root.get("project_name"), name));
            return Optional.ofNullable(session.createQuery(cr).getSingleResult());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return Optional.empty();
        }finally {
            readLock.unlock();
        }
    }
}
