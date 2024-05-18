package daoservices;

import dao.ReviewDao;
import database.DataBaseConnection;
import reviewSystem.Review;
import tourism.TouristPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryService {
    private final ReviewDao reviewDao;

    public ReviewRepositoryService(Connection connection) throws SQLException {
        this.reviewDao = new ReviewDao(connection);
    }

    public void addReview(Review review) throws SQLException {
        reviewDao.addReview(review);
    }

    public List<Review> getReviewsByUser(String username) throws SQLException {
        return reviewDao.getReviewsByUser(username);
    }

    public List<Review> getReviewsForTouristPackage(int packageId) throws SQLException {
        return reviewDao.getReviewsForTouristPackage(packageId);
    }

    public boolean removeReview(int reviewId) throws SQLException {
        return reviewDao.deleteReview(reviewId);
    }

    public boolean updateReview(int reviewId, Review updatedReview) throws SQLException {
        return reviewDao.updateReview(reviewId, updatedReview);
    }

    public Review getReviewById(int reviewId) throws SQLException {
        return reviewDao.getReviewById(reviewId);
    }

    public void close() throws SQLException {
        reviewDao.close();
    }

    public List<TouristPackage> getReservations(String username) throws SQLException {
        List<TouristPackage> rezervari = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM rezervari r JOIN tourist_package p ON r.id_pachet = p.id WHERE r.username = ?")) {
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