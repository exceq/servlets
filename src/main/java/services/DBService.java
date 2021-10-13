package services;

import dao.UsersDAO;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.SQLException;
import java.util.function.Function;

public class DBService {
    private final static SessionFactory sessionFactory;

    static {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private DBService() {
    }

    public static User getUserByLogin(String login) {
        return getUserByFunction(session -> new UsersDAO(session).getUserByLogin(login));
    }

    public static User getUserBySessionId(String sessionId) {
        return getUserByFunction(session -> new UsersDAO(session).getUserBySessionId(sessionId));
    }

    private static User getUserByFunction(Function<Session, User> handler){
        try {
            Session session = sessionFactory.openSession();
            User user = handler.apply(session);
            session.close();
            return user;
        } catch (HibernateException e) {
            //TODO ??
            return null;
        }
    }

    public static void addOrUpdateUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersDAO dao = new UsersDAO(session);
        dao.insertOrUpdateUser(user);
        transaction.commit();
        session.close();
    }

    public static void addSession(String sessionId, User user) {
        user.setSessionId(sessionId);
        addOrUpdateUser(user);
    }

    public static void deleteSession(String sessionId) {
        User user = getUserBySessionId(sessionId);
        user.setSessionId(null);
        addOrUpdateUser(user);
    }

    private static Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydb");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
