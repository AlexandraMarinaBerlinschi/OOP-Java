package dao;

import reviewSystem.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private Connection connection;
    private PreparedStatement addReviewStatement;
    private PreparedStatement getReviewsByUserStatement;
    private PreparedStatement deleteReviewStatement;
    private PreparedStatement updateReviewStatement;
    private PreparedStatement getReviewByIdStatement;
    private PreparedStatement getReviewsForTouristPackageStatement;

    public ReviewDao(Connection connection) throws SQLException {
        this.connection = connection;
        initializePreparedStatements();
    }

    private void initializePreparedStatements() throws SQLException {
        addReviewStatement = connection.prepareStatement("INSERT INTO reviews (text, rating, username, date, package_id) VALUES (?, ?, ?, ?, ?)");
        getReviewsByUserStatement = connection.prepareStatement("SELECT * FROM reviews WHERE username = ?");
        deleteReviewStatement = connection.prepareStatement("DELETE FROM reviews WHERE id = ?");
        updateReviewStatement = connection.prepareStatement("UPDATE reviews SET text = ?, rating = ? WHERE id = ?");
        getReviewByIdStatement = connection.prepareStatement("SELECT * FROM reviews WHERE id = ?");
        getReviewsForTouristPackageStatement = connection.prepareStatement("SELECT * FROM reviews WHERE package_id = ?");
    }

    public void addReview(Review review) throws SQLException {
        addReviewStatement.setString(1, review.getText());
        addReviewStatement.setDouble(2, review.getRating());
        addReviewStatement.setString(3, review.getUsername());
        addReviewStatement.setDate(4, Date.valueOf(review.getDate()));
        addReviewStatement.setInt(5, review.getPackageId());
        addReviewStatement.executeUpdate();
    }

    public List<Review> getReviewsByUser(String username) throws SQLException {
        getReviewsByUserStatement.setString(1, username);
        ResultSet resultSet = getReviewsByUserStatement.executeQuery();
        return mapResultSetToReviews(resultSet);
    }

    public boolean deleteReview(int reviewId) throws SQLException {
        deleteReviewStatement.setInt(1, reviewId);
        return deleteReviewStatement.executeUpdate() > 0;
    }

    public boolean updateReview(int reviewId, Review updatedReview) throws SQLException {
        updateReviewStatement.setString(1, updatedReview.getText());
        updateReviewStatement.setDouble(2, updatedReview.getRating());
        updateReviewStatement.setInt(3, reviewId);
        return updateReviewStatement.executeUpdate() > 0;
    }

    public Review getReviewById(int reviewId) throws SQLException {
        getReviewByIdStatement.setInt(1, reviewId);
        ResultSet resultSet = getReviewByIdStatement.executeQuery();
        return mapResultSetToReview(resultSet);
    }

    public List<Review> getReviewsForTouristPackage(int packageId) throws SQLException {
        getReviewsForTouristPackageStatement.setInt(1, packageId);
        ResultSet resultSet = getReviewsForTouristPackageStatement.executeQuery();
        return mapResultSetToReviews(resultSet);
    }
    private Review mapResultSetToReview(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String text = resultSet.getString("text");
        double rating = resultSet.getDouble("rating");
        String username = resultSet.getString("username");
        Date date = resultSet.getDate("date");
        int packageId = resultSet.getInt("package_id");

        Review review = new Review(text, rating, username, date.toLocalDate());
        review.setId(id);
        review.setPackageId(packageId);
        return review;
    }

    private List<Review> mapResultSetToReviews(ResultSet resultSet) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        while (resultSet.next()) {
            reviews.add(mapResultSetToReview(resultSet));
        }
        return reviews;
    }



    public void close() throws SQLException {
        addReviewStatement.close();
        getReviewsByUserStatement.close();
        deleteReviewStatement.close();
        updateReviewStatement.close();
        getReviewByIdStatement.close();
        getReviewsForTouristPackageStatement.close();
        connection.close();
    }
}