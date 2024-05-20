package tourism;

import reviewSystem.Review;

import java.util.ArrayList;
import java.util.List;

public class TouristPackage {
    private double pret;
    private String durata;
    private float rating;
    private Destination destinatie;

    private int destinatie_id;

    private String nume;
    private int nrPersoane;
    //private final int id;
    private int id;
    private List<Review> reviews;
    private static int lastId = 0;

    public TouristPackage(String nume, double pret, String durata, float rating, Destination destinatie, int nrPersoane) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
        this.durata = durata;
        this.rating = rating;
        this.destinatie = destinatie;
        this.nrPersoane = nrPersoane;
        this.reviews = new ArrayList<>();
    }

    public TouristPackage(int id, String nume, int destinatie_id, String durata, double pret) {
        this.id = id;
        this.nume = nume;
        this.destinatie = destinatie;
        this.durata = durata;
        this.pret = pret;
        this.reviews = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setNrPersoane(int nrPersoane) {
        this.nrPersoane = nrPersoane;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public int getNrPersoane() {
        return nrPersoane;
    }

    public String getDurata() {
        return durata;
    }

    public Destination getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(Destination destinatie) {
        this.destinatie = destinatie;
        destinatie.addTouristPackage(this);
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        recalculateRating();
    }

    public List<Review> getReviews() {
        return this.reviews;
    }

    private void recalculateRating() {
        if (reviews.isEmpty()) {
            this.rating = 0;
            return;
        }
        double ratingFinal = 0;
        for (Review review : reviews) {
            ratingFinal += review.getRating();
        }
        this.rating = (float) (ratingFinal / reviews.size());
    }

    public void aplicaDiscount(double procentajDiscount) {
        if (procentajDiscount > 0 && procentajDiscount <= 100) {
            this.pret -= this.pret * procentajDiscount / 100;
            System.out.println("Un discount de " + procentajDiscount + "% a fost aplicat. Noul pret: " + this.pret);
        }
    }
}