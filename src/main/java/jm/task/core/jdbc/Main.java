package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService us = new UserServiceImpl();
        us.createUsersTable();

        us.saveUser("Dinka", "Gaf", (byte) 20);
        us.saveUser("Linka", "Gaf", (byte) 20);
        us.saveUser("Elza", "Gaf", (byte) 20);
        us.saveUser("Fanis", "Gaf", (byte) 20);

        us.getAllUsers();
        us.cleanUsersTable();
        us.dropUsersTable();




    }

}
