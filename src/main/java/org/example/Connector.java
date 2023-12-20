package org.example;
import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;

public class Connector {
    final StandardServiceRegistry registry;
    SessionFactory sessionFactory;

    public Connector() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
