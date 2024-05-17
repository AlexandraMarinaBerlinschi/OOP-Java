package daoservices;

import dao.TouristPackageDao;
import tourism.Destination;
import tourism.TouristPackage;
import database.DataBaseConnection;
import exceptions.PackageNotFoundException;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class TourismRepositoryService {
    private final TouristPackageDao packageDao;
    private Connection connection;

    public TourismRepositoryService() {
        this.connection = DataBaseConnection.getConnection();
        this.packageDao = new TouristPackageDao(connection);
    }
    public int addPackage(TouristPackage touristPackage) {
        return packageDao.addPackage(touristPackage);
    }
    public int addDestination(Destination destination) {
        String sql = "INSERT INTO destination (tara, tip_atractie, activitati, nume_destinatie) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, destination.getTara());
            pstmt.setString(2, destination.getTipAtractie());
            pstmt.setString(3, destination.getActivitati());
            pstmt.setString(4, destination.getNumeDestinatie());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<TouristPackage> getAllPackages() {
        return packageDao.getAllPackages();
    }

    public TouristPackage getPackageById(int id) {
        return packageDao.getPackageById(id);
    }

    public boolean updatePackage(TouristPackage updatedPackage) {
        try {
            packageDao.updatePackage(updatedPackage);
            return true;
        } catch (PackageNotFoundException e) {
            System.err.println("Package not found: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePackage(int id) {
        Connection conn = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);

            TouristPackage packageToDelete = packageDao.getPackageById(id);
            if (packageToDelete == null) {
                throw new PackageNotFoundException("Pachetul turistic cu id-ul " + id + " nu a fost gasit in baza de date.");
            }

            packageDao.deletePackage(id, conn);
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } catch (PackageNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public List<TouristPackage> searchByName(String name) {
        return packageDao.getAllPackages().stream()
                .filter(p -> p.getNume().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<TouristPackage> searchByDestination(String destination) {
        return packageDao.getAllPackages().stream()
                .filter(p -> p.getDestinatie().getNumeDestinatie().toLowerCase().contains(destination.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<TouristPackage> filterPackagesByPrice(float minPrice, float maxPrice) {
        return packageDao.getAllPackages().stream()
                .filter(p -> p.getPret() >= minPrice && p.getPret() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<TouristPackage> filterPackagesByRating(float minRating, float maxRating) {
        return packageDao.getAllPackages().stream()
                .filter(p -> p.getRating() >= minRating && p.getRating() <= maxRating)
                .collect(Collectors.toList());
    }
}