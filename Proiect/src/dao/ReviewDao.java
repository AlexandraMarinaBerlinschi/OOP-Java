package dao;

import reviewSystem.Review;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewDao {
    private final List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        if (review != null) {
            reviews.add(review);
        }
    }

    public List<Review> getReviewsByUser(String username) {
        return reviews.stream()
                .filter(review -> review.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public boolean deleteReview(int reviewId) {
        return reviews.removeIf(review -> review.getId() == reviewId);
    }

    public boolean updateReview(int reviewId, Review updatedReview) {
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getId() == reviewId) {
                reviews.set(i, updatedReview);
                return true;
            }
        }
        return false;
    }

    public Review getReviewById(int reviewId) {
        return reviews.stream()
                .filter(review -> review.getId() == reviewId)
                .findFirst()
                .orElse(null);
    }

    public List<Review> getReviewsForTouristPackage(int packageId) {
        return reviews.stream()
                .filter(review -> review.getPackageId() == packageId)
                .collect(Collectors.toList());
    }
}

