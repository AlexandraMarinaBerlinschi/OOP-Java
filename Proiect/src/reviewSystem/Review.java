package reviewSystem;

import java.time.LocalDate;

public class Review {
    private static int nextId = 1;
    private int id;
    private String text;
    private double rating;
    private String username;
    private LocalDate date;
    private int packageId;


    public Review(String text, double rating, String username, LocalDate date) {
        this.id = nextId++;
        this.text = text;
        this.rating = rating;
        this.username = username;
        this.date = date;
        this.packageId = packageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
