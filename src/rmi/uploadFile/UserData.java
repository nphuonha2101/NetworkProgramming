package rmi.uploadFile;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    public static List<User> getAll() {
       List<User> userList = new ArrayList<>();

       User usr1 = new User("nphuonha", "1234") ;
       userList.add(usr1);

       return userList;
    }
}
