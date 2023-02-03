package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session s = Util.getSessionFactory().openSession()){
            s.beginTransaction();
            String SQL = "CREATE TABLE IF NOT EXISTS USERS" +
                    "(ID BIGINT not NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), lastName VARCHAR(255), age TINYINT UNSIGNED)";
            s.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session s = Util.getSessionFactory().openSession()) {
            s.beginTransaction();
            s.createSQLQuery("DROP TABLE IF EXISTS USERS").addEntity(User.class).executeUpdate();
            s.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction trans = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            trans = s.getTransaction();
            trans.begin();
            s.save(new User(name, lastName, age));
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session s = Util.getSessionFactory().openSession()){
            User user = new User();
            user = s.get(User.class, user.getId());
            s.remove(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction trans = null;
        try (Session s = Util.getSessionFactory().openSession()){
            Query<User> query = s.createQuery("from User", User.class);
            users = query.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction trans = null;
        try (Session s = Util.getSessionFactory().openSession()){
            trans = s.getTransaction();
            trans.begin();
            s.createNativeQuery("TRUNCATE table USERS").addEntity(User.class).executeUpdate();
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
        }
    }
}
