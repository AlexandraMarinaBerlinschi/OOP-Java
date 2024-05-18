package dao;

import database.DataBaseConnection;
import tourism.TouristPackage;
import user.NormalUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;




public class NormalUserDao {
    private static final String INSERT_USER_QUERY = "INSERT INTO user (username, password, type, redeem_code, cod_unic) VALUES (?, ?, 'normal', ?, ?)";
    private static final String SELECT_USER_QUERY = "SELECT username, password, redeem_code, cod_unic, referinte, anulare_gratuita, discount_procentaj FROM user WHERE username = ? AND type = 'normal'";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET password = ?, redeem_code = ?, cod_unic = ?, referinte = ?, anulare_gratuita = ?, discount_procentaj = ? WHERE username = ? AND type = 'normal'";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE username = ? AND type = 'normal'";
    private static final String SELECT_USER_BY_REDEEM_CODE_QUERY = "SELECT username, password, redeem_code, cod_unic, referinte, anulare_gratuita, discount_procentaj FROM user WHERE redeem_code = ? AND type = 'normal'";
    private static final String INSERT_RESERVATION_QUERY = "INSERT INTO rezervari (username, id_pachet) VALUES (?, ?)";
    private static final String GET_RESERVATIONS_QUERY = "SELECT p.* FROM rezervari r JOIN tourist_package p ON r.id_pachet = p.id WHERE r.username = ?";

    public boolean addUser(NormalUser user) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER_QUERY)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRedeemCode());
            stmt.setString(4, user.getCodUnic());
            stmt.executeUpdate();
            return true;
        }
    }

    public NormalUser getUser(String username) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_QUERY)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                NormalUser user = new NormalUser(username, rs.getString("password"), rs.getString("redeem_code"));
                user.setCodUnic(rs.getString("cod_unic"));
                user.setReferinte(rs.getInt("referinte"));
                user.setAnulareGratuita(rs.getBoolean("anulare_gratuita"));
                user.setDiscountProcentaj(rs.getInt("discount_procentaj"));
                return user;
            }
        }
        return null;
    }

    public boolean updateUser(NormalUser user) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_USER_QUERY)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getRedeemCode());
            stmt.setString(3, user.getCodUnic());
            stmt.setInt(4, user.getReferinte());
            stmt.setBoolean(5, user.isAnulareGratuita());
            stmt.setInt(6, user.getDiscountProcentaj());
            stmt.setString(7, user.getUsername());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(String username) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_USER_QUERY)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        }
    }

    public void insertReservation(String username, int pachetId) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_RESERVATION_QUERY)) {
            stmt.setString(1, username);
            stmt.setInt(2, pachetId);
            stmt.executeUpdate();
        }
    }
    public NormalUser getUserByRedeemCode(String redeemCode) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_REDEEM_CODE_QUERY)) {
            stmt.setString(1, redeemCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                NormalUser user = new NormalUser(rs.getString("username"), rs.getString("password"), redeemCode);
                user.setCodUnic(rs.getString("cod_unic"));
                user.setReferinte(rs.getInt("referinte"));
                user.setAnulareGratuita(rs.getBoolean("anulare_gratuita"));
                user.setDiscountProcentaj(rs.getInt("discount_procentaj"));
                return user;
            }
        }
        return null;
    }

    public List<NormalUser> getAllNormalUsers() throws SQLException {
        List<NormalUser> normalUsers = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT username, password, redeem_code, cod_unic, referinte, anulare_gratuita, discount_procentaj FROM user WHERE type = 'normal'");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NormalUser user = new NormalUser(rs.getString("username"), rs.getString("password"), rs.getString("redeem_code"));
                user.setCodUnic(rs.getString("cod_unic"));
                user.setReferinte(rs.getInt("referinte"));
                user.setAnulareGratuita(rs.getBoolean("anulare_gratuita"));
                user.setDiscountProcentaj(rs.getInt("discount_procentaj"));
                normalUsers.add(user);
            }
        }
        return normalUsers;
    }

    public List<TouristPackage> getReservations(String username) throws SQLException {
        List<TouristPackage> rezervari = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_RESERVATIONS_QUERY)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TouristPackage pachet = new TouristPackage(rs.getInt("id"), rs.getString("nume"), rs.getInt("destinatie_id"), rs.getString("durata"), rs.getDouble("pret"));
                rezervari.add(pachet);
            }
        }
        return rezervari;
    }
}