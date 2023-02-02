package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.mapping.Property;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:MySQL://localhost/kata_base";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String SQL_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Class.forName(DB_DRIVER);
        conn = DriverManager.getConnection(DB_URL,DB_USERNAME, DB_PASSWORD);

        return conn;
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DB_DRIVER);
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, DB_USERNAME);
                settings.put(Environment.PASS, DB_PASSWORD);
                settings.put(Environment.DIALECT, SQL_DIALECT);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
