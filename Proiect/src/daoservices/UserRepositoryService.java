package daoservices;

import dao.AdminDao;
import dao.NormalUserDao;
import tourism.TouristPackage;
import user.Admin;
import user.NormalUser;

import java.sql.SQLException;
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
        try {
            adminDao.addAdmin(admin);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean addNormalUser(NormalUser normalUser) {
        try {
            return normalUserDao.addUser(normalUser);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Admin> getAdmins() {
        return new ArrayList<>(admins);
    }

    public List<NormalUser> getNormalUsers() {
        return new ArrayList<>(normalUsers);
    }

    public List<TouristPackage> getReservations(String username) throws SQLException {
        return normalUserDao.getReservations(username);
    }

    public Admin getAdminByUsername(String username) {
        try {
            return adminDao.getAdmin(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public NormalUser getNormalUserByUsername(String username) {
        try {
            return normalUserDao.getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeAdmin(String username) {
        try {
            adminDao.deleteAdmin(username);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getTotalSales() throws SQLException {
        double totalSales = 0.0;
        List<NormalUser> normalUsers = normalUserDao.getAllNormalUsers(); // actualizare cu utilizatorii din baza de date
        for (NormalUser user : normalUsers) {
            List<TouristPackage> reservations = normalUserDao.getReservations(user.getUsername());
            for (TouristPackage reservation : reservations) {
                totalSales += reservation.getPret();
            }
        }
        return totalSales;
    }
    public boolean removeNormalUser(String username) {
        try {
            return normalUserDao.deleteUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAdmin(String username, Admin updatedAdmin) {
        try {
            adminDao.updateAdmin(updatedAdmin);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateNormalUser(String username, NormalUser updatedNormalUser) {
        try {
            return normalUserDao.updateUser(updatedNormalUser);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveReservation(String username, int pachetId) throws SQLException {
        normalUserDao.insertReservation(username, pachetId);
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
