package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement stm = Util.getConnection().createStatement()) {
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS USERS(ID BIGINT not NULL PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255), LAST_NAME VARCHAR(255), AGE TINYINT UNSIGNED)");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try(Statement stm = Util.getConnection().createStatement()) {
            stm.executeUpdate("DROP TABLE IF EXISTS USERS");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        long ID;
        try (Connection conn = Util.getConnection();
            PreparedStatement prst = conn.prepareStatement("INSERT INTO USERS(NAME, LAST_NAME, AGE) VALUES(?, ?, ?)")) {

            prst.setString(1, name);
            prst.setString(2, lastName);
            prst.setByte(3, age);
            prst.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM USERS WHERE ID = ?")) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement stm = Util.getConnection().createStatement()){
            ResultSet rs = stm.executeQuery("SELECT ID, NAME, LAST_NAME, AGE FROM USERS");
            while (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                user.setId(rs.getLong(1));
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement stm = Util.getConnection().createStatement()){
            stm.executeUpdate("TRUNCATE table USERS");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
