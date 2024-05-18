package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tourism.Destination;
import tourism.TouristPackage;
import exceptions.PackageNotFoundException;

public class TouristPackageDao {
    private static final String INSERT_PACKAGE_QUERY = "INSERT INTO tourist_package (id, nume, pret, durata, rating, nr_persoane, destinatie_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_PACKAGE_BY_ID_QUERY = "SELECT * FROM tourist_package WHERE id = ?";
    private static final String GET_ALL_PACKAGES_QUERY = "SELECT * FROM tourist_package";
    private static final String UPDATE_PACKAGE_QUERY = "UPDATE tourist_package SET nume = ?, pret = ?, durata = ?, rating = ?, nr_persoane = ?, destinatie_id = ? WHERE id = ?";
    private static final String DELETE_PACKAGE_QUERY = "DELETE FROM tourist_package WHERE id = ?";

    private static final String INSERT_DESTINATION_QUERY = "INSERT INTO destination (tara, tip_atractie, activitati, nume_destinatie) VALUES (?, ?, ?, ?)";
    private static final String GET_DESTINATION_BY_NAME_QUERY = "SELECT id FROM destination WHERE nume_destinatie = ?";

    private Connection connection;

    public TouristPackageDao() {
    }

    public TouristPackageDao(Connection connection) {
        this.connection = connection;
    }

    public void addPackage(TouristPackage touristPackage, Connection connection) {
        this.connection = connection;
    }

    public int addPackage(TouristPackage touristPackage) {
        if (touristPackage == null) {
            return -1;
        }

        int destinatieId = getOrInsertDestinationId(touristPackage.getDestinatie());
        if (destinatieId == -1) {
            return -1;
        }

        touristPackage.getDestinatie().setId(destinatieId);

        try (PreparedStatement statement = connection.prepareStatement(INSERT_PACKAGE_QUERY)) {
            statement.setInt(1, touristPackage.getId()); // Setează ID-ul furnizat de utilizator
            statement.setString(2, touristPackage.getNume());
            statement.setDouble(3, touristPackage.getPret());
            statement.setString(4, touristPackage.getDurata());
            statement.setFloat(5, touristPackage.getRating());
            statement.setInt(6, touristPackage.getNrPersoane());
            statement.setInt(7, destinatieId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return -1;
            }

            return touristPackage.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private Destination getDestinationById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM destination WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int destinationId = resultSet.getInt("id");
                String tara = resultSet.getString("tara");
                String tipAtractie = resultSet.getString("tip_atractie");
                String activitati = resultSet.getString("activitati");
                String numeDestinatie = resultSet.getString("nume_destinatie");

                Destination destination = new Destination(numeDestinatie);
                destination.setTara(tara);
                destination.setTipAtractie(tipAtractie);
                destination.setActivitati(activitati);

                return destination;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TouristPackage getPackageById(int id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_PACKAGE_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int packageId = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                double pret = resultSet.getDouble("pret");
                String durata = resultSet.getString("durata");
                float rating = resultSet.getFloat("rating");
                int nrPersoane = resultSet.getInt("nr_persoane");
                int destinatieId = resultSet.getInt("destinatie_id");

                Destination destinatie = getDestinationById(destinatieId);

                TouristPackage touristPackage = new TouristPackage(nume, pret, durata, rating, destinatie, nrPersoane);
                touristPackage.setId(packageId);
                return touristPackage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<TouristPackage> getAllPackages() {
        List<TouristPackage> packages = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_PACKAGES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int packageId = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                double pret = resultSet.getDouble("pret");
                String durata = resultSet.getString("durata");
                float rating = resultSet.getFloat("rating");
                int nrPersoane = resultSet.getInt("nr_persoane");
                int destinatieId = resultSet.getInt("destinatie_id");

                Destination destinatie = getDestinationById(destinatieId);

                TouristPackage touristPackage = new TouristPackage(nume, pret, durata, rating, destinatie, nrPersoane);
                touristPackage.setId(packageId);
                packages.add(touristPackage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return packages;
    }

    public void updatePackage(TouristPackage updatedPackage) throws SQLException, PackageNotFoundException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PACKAGE_QUERY)) {
            statement.setString(1, updatedPackage.getNume());
            statement.setDouble(2, updatedPackage.getPret());
            statement.setString(3, updatedPackage.getDurata());
            statement.setFloat(4, updatedPackage.getRating());
            statement.setInt(5, updatedPackage.getNrPersoane());
            statement.setInt(6, getOrInsertDestinationId(updatedPackage.getDestinatie()));
            statement.setInt(7, updatedPackage.getId()); // Utilizăm ID-ul din obiectul TouristPackage

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new PackageNotFoundException("Pachetul turistic cu id-ul " + updatedPackage.getId() + " nu a fost gasit in baza de date.");
            }
        }
    }

    public void deletePackage(int id, Connection conn) throws PackageNotFoundException, SQLException {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_PACKAGE_QUERY)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new PackageNotFoundException("Pachetul turistic cu id-ul " + id + " nu a fost gasit in baza de date.");
            }
        }
    }

    private int getOrInsertDestinationId(Destination destinatie) {
        try (PreparedStatement statement = connection.prepareStatement(GET_DESTINATION_BY_NAME_QUERY)) {
            statement.setString(1, destinatie.getNumeDestinatie());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_DESTINATION_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, destinatie.getTara());
            statement.setString(2, destinatie.getTipAtractie());
            statement.setString(3, destinatie.getActivitati());
            statement.setString(4, destinatie.getNumeDestinatie());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserarea destinației a esuat, nu s-a returnat niciun ID.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserarea destinației a esuat, nu s-a generat niciun ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}