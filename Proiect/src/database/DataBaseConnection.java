package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        {
            try {
                String url = "jdbc:mysql://localhost/demo";
                String user = "root";
                String password = "proiect";
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Eroare la stabilirea conexiunii la baza de date!");
                e.printStackTrace();
            }
            return null;
        }
    }

}
