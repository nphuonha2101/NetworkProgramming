package io.fileTransfer.userServices;

import java.util.HashMap;
import java.util.Map;

public class UserServices {
    HashMap<String, String> userData;
    private static UserServices instance;

    public static UserServices getInstance() {
        if (instance == null)
            instance = new UserServices();
        return instance;
    }

    private UserServices() {
        userData = new HashMap<>();

        userData.put("nva", "1234");
        userData.put("nvb", "1234");
        userData.put("nvc", "2345");
    }

    public String authUsername(String username) {
        for (Map.Entry<String, String> entry: userData.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(username))
                return username;
        }
        return null;
    }

    public boolean authLogin(String username, String passwd) {
        for (Map.Entry<String, String> entry: userData.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(username) && entry.getValue().equalsIgnoreCase(passwd))
                return true;
        }
        return false;
    }
}
