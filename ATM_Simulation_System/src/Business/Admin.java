package Business;

import Data_Access.MainDAL;

public class Admin extends Role{
    Admin(String name,Integer ID){
        super(name,ID,ROLE_TYPE.ADMIN);
    }
    public boolean delete_account(Integer ID){
        return MainDAL.write("Delete from Account Where Id=" + ID);
    }
    public boolean add_account(User user,String pin){
        return MainDAL.write(String.format("Insert into Account (Pin,Balance,Name,UserId) Values('%s',%f,'%s',%d)",pin,0.0,user.getName(),user.getID()));
    }
//    public boolean block_account(Integer ID){
//        return true;
//    }
    public boolean add_user(String name){
        return MainDAL.write(String.format("Insert into User (Name,Password,UserType) Values ('%s','%s','%s')",name,"123456",ROLE_TYPE.USER.toString()));
    }
//    public boolean update_user(User user){
//        return true;
//    }
    public boolean delete_user(Integer ID){
        return MainDAL.write("Delete from User Where Id=" + ID);
    }
//    public boolean update_account(){
//
//    }
//    public static void main(String[] args){
//        Admin ad = new Admin("jj",1);
//        User user = new User("sss",5);
//        ad.add_account(user,"1472");
//    }
}
