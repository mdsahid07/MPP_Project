package Business;

import Data_Access.MainDAL;
import UI.Managment.UserList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SystemModel {
    public List<Role> roles;
    public static Role role = new Admin("ADMIN",1);

    public SystemModel() {
        roles = new ArrayList<Role>();
        role = null;
    }

    public Role VerifyLogin(String username, String password) {
        // If user is invalid than return null
        boolean isValidUser = false;
        String userTypeStr = "";
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmsystem", "root", "123456");
            Statement statement = con.createStatement();
            ResultSet query = statement.executeQuery("Select * from User");

            while (query.next()) {
                String name = query.getString("name"); // Column "name" (String)
                String pwd = query.getString("password");
                String userType = query.getString("userType");
                if (username.equals(name) && password.equals(pwd) && userType.toUpperCase().equals("ADMIN")) {
                    isValidUser = true;
                    userTypeStr = userType;

                    break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (isValidUser) {
            if (userTypeStr.toUpperCase().equals(ROLE_TYPE.ADMIN.toString())) {
                role = new Admin("Admin", 1);
            } else {
                role = new User("User", 2);
            }

            //Manage the session
            SessionManager.getInstance().setUsername(username);


        }
        return role;
    }
    public static List<User> getUserList() {
        try {
            List<User> list = new ArrayList<>();
            ResultSet query = MainDAL.read(String.format("Select * from User Where UserType='%s'",ROLE_TYPE.USER.toString()));
            while (query.next()) {
                list.add(new User(query.getString("name"), query.getInt("Id")));
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static List<Object []> getAccountList(){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet query = MainDAL.read(String.format("Select * from Account"));
            while (query.next()) {
                Object[] row = new Object[3];
                row[0] = query.getInt("AccNumber");
                row[1] = query.getDouble("Balance");
                row[2] = query.getString("Name");
                list.add(row);
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
