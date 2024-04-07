package dao;

import user.NormalUser;
import java.util.HashMap;
import java.util.Map;


public class NormalUserDao {

    private final Map<String, NormalUser> users = new HashMap<>();

    public boolean addUser(NormalUser user) {
        if (user == null || users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }

    public NormalUser getUser(String username) {
        return users.get(username);
    }

    public boolean updateUser(NormalUser user) {
        if (user != null && users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    public boolean deleteUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            return true;
        }
        return false;
    }

}
