package daoservices;

import dao.ReviewDao;
import reviewSystem.Review;
import java.util.List;

public class ReviewRepositoryService {
    private final ReviewDao reviewDao;

    public ReviewRepositoryService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }


    public void addReview(Review review) {
        reviewDao.addReview(review);
    }

    public List<Review> getReviewsByUser(String username) {
        return reviewDao.getReviewsByUser(username);
    }

    public List<Review> getReviewsForTouristPackage(int packageId) {
        return reviewDao.getReviewsForTouristPackage(packageId);
    }

    public boolean removeReview(int reviewId) {
        return reviewDao.deleteReview(reviewId);
    }

    public boolean updateReview(int reviewId, Review updatedReview) {
        return reviewDao.updateReview(reviewId, updatedReview);
    }

    public Review getReviewById(int reviewId) {
        return reviewDao.getReviewById(reviewId);
    }
}
