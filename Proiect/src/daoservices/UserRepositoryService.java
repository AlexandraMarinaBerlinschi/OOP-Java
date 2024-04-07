package daoservices;

import dao.AdminDao;
import dao.NormalUserDao;
import user.Admin;
import user.NormalUser;

import java.util.List;
import java.util.ArrayList;

public class UserRepositoryService {
    private final List<Admin> admins = new ArrayList<>();
    private final List<NormalUser> normalUsers = new ArrayList<>();
    private final AdminDao adminDao;
    private final NormalUserDao normalUserDao;

    public UserRepositoryService() {
        this.adminDao = new AdminDao();
        this.normalUserDao = new NormalUserDao();
    }

    public boolean addAdmin(Admin admin) {
        if (admin != null && adminDao.getAdmin(admin.getUsername()) == null) {
            admins.add(admin);
            adminDao.addAdmin(admin);
            return true;
        }
        return false;
    }

    public boolean addNormalUser(NormalUser normalUser) {
        if (normalUser != null && normalUserDao.getUser(normalUser.getUsername()) == null) {
            normalUsers.add(normalUser);
            normalUserDao.addUser(normalUser);
            return true;
        }
        return false;
    }

    public List<Admin> getAdmins() {
        return new ArrayList<>(admins);
    }

    public List<NormalUser> getNormalUsers() {
        return new ArrayList<>(normalUsers);
    }

    public Admin getAdminByUsername(String username) {
        return admins.stream()
                .filter(admin -> admin.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public NormalUser getNormalUserByUsername(String username) {
        return normalUsers.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public boolean removeAdmin(String username) {
        if (adminDao.getAdmin(username) != null) {
            adminDao.deleteAdmin(username);
            return admins.removeIf(admin -> admin.getUsername().equals(username));
        }
        return false;
    }

    public boolean removeNormalUser(String username) {
        if (normalUserDao.getUser(username) != null) {
            normalUserDao.deleteUser(username);
            return normalUsers.removeIf(user -> user.getUsername().equals(username));
        }
        return false;
    }

    public boolean updateAdmin(String username, Admin updatedAdmin) {
        if (adminDao.getAdmin(username) != null) {
            adminDao.updateAdmin(updatedAdmin);
            for (int i = 0; i < admins.size(); i++) {
                if (admins.get(i).getUsername().equals(username)) {
                    admins.set(i, updatedAdmin);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateNormalUser(String username, NormalUser updatedNormalUser) {
        if (normalUserDao.getUser(username) != null) {
            normalUserDao.updateUser(updatedNormalUser);
            for (int i = 0; i < normalUsers.size(); i++) {
                if (normalUsers.get(i).getUsername().equals(username)) {
                    normalUsers.set(i, updatedNormalUser);
                    return true;
                }
            }
        }
        return false;
    }

    public NormalUser getNormalUserByRedeemCode(String redeemCode) {
        return normalUsers.stream()
                .filter(user -> user.getCodUnic().equals(redeemCode))
                .findFirst()
                .orElse(null);
    }

    public List<NormalUser> getAllNormalUsers() {
        return normalUsers;
    }
}
