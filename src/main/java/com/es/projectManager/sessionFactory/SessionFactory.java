package com.es.projectManager.sessionFactory;

import com.es.projectManager.model.Project;
import com.es.projectManager.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionFactory {
    private static org.hibernate.SessionFactory sessionFactory;

    private SessionFactory() {}

    public static org.hibernate.SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Project.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("error" + e);
            }
        }
        return sessionFactory;
    }
}
