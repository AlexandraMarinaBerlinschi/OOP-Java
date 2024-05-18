package dao;

import user.Admin;
import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    private static final String INSERT_ADMIN_QUERY = "INSERT INTO user (username, password, type) VALUES (?, ?, 'admin')";
    private static final String SELECT_ADMIN_QUERY = "SELECT username, password FROM user WHERE username = ? AND type = 'admin'";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE user SET password = ? WHERE username = ? AND type = 'admin'";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM user WHERE username = ? AND type = 'admin'";

    public void addAdmin(Admin admin) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ADMIN_QUERY)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.executeUpdate();
        }
    }

    public Admin getAdmin(String username) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ADMIN_QUERY)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(username, rs.getString("password"));
            }
        }
        return null;
    }

    public void updateAdmin(Admin admin) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_ADMIN_QUERY)) {
            stmt.setString(1, admin.getPassword());
            stmt.setString(2, admin.getUsername());
            stmt.executeUpdate();
        }
    }

    public void deleteAdmin(String username) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_ADMIN_QUERY)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}
