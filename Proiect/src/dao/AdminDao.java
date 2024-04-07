package dao;

import user.Admin;

import java.util.HashMap;
import java.util.Map;

public class AdminDao {
    private final Map<String, Admin> adminMap = new HashMap<>();

    public Admin getAdmin(String username) {
        return adminMap.get(username);
    }

    public void addAdmin(Admin admin) {
        if (admin != null && !adminMap.containsKey(admin.getUsername())) {
            adminMap.put(admin.getUsername(), admin);
        }
    }

    public void updateAdmin(Admin admin) {
        if (admin != null && adminMap.containsKey(admin.getUsername())) {
            adminMap.put(admin.getUsername(), admin);
        }
    }

    public void deleteAdmin(String username) {
        adminMap.remove(username);
    }

}