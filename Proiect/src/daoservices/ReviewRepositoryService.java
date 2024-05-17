package daoservices;

import dao.ReviewDao;
import reviewSystem.Review;

import java.sql.Connection;
import java.sql.SQLException;
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
}