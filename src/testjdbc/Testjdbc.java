/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjdbc;
import java.sql.*;
import java.util.*;
import domain.entities.*;
import testjdbc.service.*;
/**groupid ru.ugrasu
 * hibernatetest
 * 
 *
 * @author ods1531b
 */
public class Testjdbc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
               Map<Long, User> userList = UserService.getInstance().getAll();
        Iterator<Map.Entry<Long, User>> iterator = userList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, User> entry = iterator.next();
            User user = entry.getValue();
            if (user.getRole() == null) {
                System.out.println("User name: " + user.getLogin());
            } else {
                for (Role role : user.getRole()) {
                    System.out.println("User name: " + user.getLogin() + " role: " + role.getName());
                }
            }
        }


    
    }
    
}
