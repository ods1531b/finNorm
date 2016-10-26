/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjdbc.service;
import java.sql.*;
import java.util.*;
import domain.entities.*;
/**
 *
 * @author ods1531b
 */
public class UserService {
      private ResultSet rs;
    public static UserService userService;

    private UserService() {
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public User getById(long id) {
        String query = "select * from Фин.аналитики where ID user = " + id;
        User returnedUser = new User();
        try {
            rs = MySQLConnection.getInstance().executeQuery(query);

            while (rs.next()) {
                returnedUser.setId(rs.getLong("ID user"));
                returnedUser.setName(rs.getString("login"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnedUser;
    }

    public Map<Long, User> getAll() {
        String query =
                "select [ID user] as ID , login,Роли.id_role as roles_id,Роли.[Название роли] as roles_name\n" +
     "from [Фин.аналитики]\n"+
                "inner join Роли on Роли.ID_user=[Фин.аналитики].[ID user]";
        //коллекция пользователей, в качествен идентификатора будет id пользователя
        Map<Long, User> userList = new HashMap<Long, User>();
        try {
            rs = MySQLConnection.getInstance().executeQuery(query);
            while (rs.next()) {
                Long userId = rs.getLong("ID");
                //Получаем пользователя из коллекции, если мы его уже обрабатывали
                User user = userList.get(userId);
                if (user == null) {
                    user = new User();
                    user.setId(userId);
                    
                    user.setLogin(rs.getString("login"));
                }

                Long roleId = rs.getLong("roles_id");
                //проверяем есть ли у пользователя роли
                if (roleId != 0) {
                    //получаем список ролей пользователя
                    List<Role> roles = user.getRole();
                    if (roles == null) {
                        //если список ролей пуст, инициализируем его
                        roles = new ArrayList<>();
                        user.setRole(roles);
                    }
                    //получаем роль из запроса
                    Role role = new Role();
                    role.setId(roleId);
                    role.setName(rs.getString("roles_name"));
                    //добавляем роль в список ролей пользователя
                    roles.add(role);
                }
                /*Добавляем пользователя в коллекцию. Еслм пользователь был в коллекции,
                * то объект пользователя будет заменен обновленным пользователем*/
                userList.put(userId,user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
