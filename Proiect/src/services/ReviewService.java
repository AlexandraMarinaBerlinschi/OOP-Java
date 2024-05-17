package services;

import dao.ReviewDao;
import tourism.TouristPackage;
import user.NormalUser;
import reviewSystem.Review;
import daoservices.ReviewRepositoryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class ReviewService {
    private TourismService tourismService;
    private ReviewRepositoryService reviewRepositoryService;
    private Scanner scanner;

    public ReviewService(TourismService tourismService, Connection connection) throws SQLException {
        this.tourismService = tourismService;
        this.reviewRepositoryService = new ReviewRepositoryService(connection);
        this.scanner = new Scanner(System.in);
    }

    public void adaugaRecenzie(NormalUser user) {
        try {
            TouristPackage pachet = obtinePachet(user);
            if (pachet == null) {
                return;
            }
            System.out.println("Introduceti textul recenziei:");
            String text = scanner.nextLine();
            System.out.println("Introduceti ratingul (de la 1 la 5):");
            double rating = scanner.nextDouble();
            scanner.nextLine();

            if (user.getPacheteRezervate().contains(pachet)) {
                Review review = new Review(text, rating, user.getUsername(), LocalDate.now());
                reviewRepositoryService.addReview(review);
                pachet.addReview(review);
                System.out.println("Recenzia a fost adaugata cu succes!");
            } else {
                System.out.println("Nu puteti adauga o recenzie pentru un pachet pe care nu l-ati rezervat.");
            }
        } catch (SQLException e) {
            System.out.println("Eroare la adaugarea recenziei: " + e.getMessage());
        }
    }

    private TouristPackage obtinePachet(NormalUser user) {
        System.out.println("Alegeti un pachet din rezervarile dvs caruia vreti sa ii lasati o recenzie");
        List<TouristPackage> pacheteRezervate = user.getPacheteRezervate();
        for (int i = 0; i < pacheteRezervate.size(); i++) {
            System.out.println((i + 1) + ". " + pacheteRezervate.get(i).getNume());
        }
        int alegere = citesteInt("Alegeti numarul pachetului");
        if (alegere > 0 && alegere <= pacheteRezervate.size()) {
            return pacheteRezervate.get(alegere - 1);
        } else {
            System.out.println("Alegere invalida");
            return null;
        }
    }

    public void afiseazaRecenziiUtilizator(NormalUser user) throws SQLException {
        List<Review> userReviews = reviewRepositoryService.getReviewsByUser(user.getUsername());
        if (userReviews.isEmpty()) {
            System.out.println("Nu exista recenzii lasate de " + user.getUsername());
        } else {
            userReviews.forEach(review -> {
                System.out.println("Rating: " + review.getRating() + " / 5 - " + review.getText() + " Scrisa la data " + review.getDate());
            });
        }
    }

    public void stergeRecenzie(NormalUser user) {
        try {
            List<Review> userReviews = reviewRepositoryService.getReviewsByUser(user.getUsername());
            if (userReviews.isEmpty()) {
                System.out.println("Nu aveti recenzii de sters.");
                return;
            }

            System.out.println("Selectati o recenzie pentru a o sterge:");
            afisareRecenzii(userReviews);

            int alegereReview = citesteInt("Alegeti numarul recenziei pentru stergere:");
            if (alegereReview < 1 || alegereReview > userReviews.size()) {
                System.out.println("Alegere invalida.");
                return;
            }

            Review reviewSelectat = userReviews.get(alegereReview - 1);
            reviewRepositoryService.removeReview(reviewSelectat.getId());
            System.out.println("Recenzia a fost stearsa cu succes.");
        } catch (SQLException e) {
            System.out.println("Eroare la obtinerea recenziilor utilizatorului: " + e.getMessage());
        }
    }

    public void actualizeazaRecenzie(NormalUser user) throws SQLException {
        try {
            List<Review> userReviews = reviewRepositoryService.getReviewsByUser(user.getUsername());
            if (userReviews.isEmpty()) {
                System.out.println("Nu aveti recenzii de actualizat.");
                return;
            }

            System.out.println("Selectati o recenzie pentru a o actualiza:");
            afisareRecenzii(userReviews);

            int alegereReview = citesteInt("Alegeti numarul recenziei pentru actualizare:");
            if (alegereReview < 1 || alegereReview > userReviews.size()) {
                System.out.println("Alegere invalida.");
                return;
            }

            Review reviewSelectat = userReviews.get(alegereReview - 1);
            System.out.println("Introduceti noul text al recenziei:");
            String newText = scanner.nextLine();
            System.out.println("Introduceti noul rating (de la 1 la 5):");
            double newRating = scanner.nextDouble();
            scanner.nextLine();

            Review updatedReview = new Review(newText, newRating, reviewSelectat.getUsername(), reviewSelectat.getDate());
            reviewRepositoryService.updateReview(reviewSelectat.getId(), updatedReview);
            System.out.println("Recenzia a fost actualizata cu succes.");
        } catch (SQLException e) {
            System.out.println("Eroare la actualizarea recenziilor utilizatorului: " + e.getMessage());
        }

    }

    private void afisarePacheteCuRecenzii(NormalUser user) {
        try {
            int index = 1;
            for (TouristPackage pachet : user.getPacheteRezervate()) {
                if (!reviewRepositoryService.getReviewsForTouristPackage(pachet.getId()).isEmpty()) {
                    System.out.println(index + ". " + pachet.getNume());
                    index++;
                }
            }
        } catch (SQLException e) {
            System.out.println("Eroare la obtinerea recenziilor pachetelor: " + e.getMessage());
        }

    }

    public void afisareRecenzii(List<Review> reviews) {
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            System.out.println((i + 1) + ". Rating: " + review.getRating() + " / 5 - " + review.getText() + " Scrisa de " + review.getUsername() + " la data " + review.getDate());
        }
    }

    private int citesteInt(String mesaj) {
        while (true) {
            try {
                System.out.println(mesaj);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Va rugam introduceti un numar intreg valid.");
            }
        }
    }
}