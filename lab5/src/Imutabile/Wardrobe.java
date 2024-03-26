package Imutabile;

public class Wardrobe {
    private int numberOfShirts;
    private Shirt shirt;

    public Wardrobe(int numberOfShirts, Shirt shirt) {
        this.numberOfShirts = numberOfShirts;
        this.shirt = shirt;
    }

    public int getNumberOfShirts() {
        return numberOfShirts;
    }

    public void setNumberOfShirts(int numberOfShirts) {
        this.numberOfShirts = numberOfShirts;
    }

    public Shirt getShirt() {
        return shirt;
    }

    public void setShirt(Shirt shirt) {
        this.shirt = shirt;
    }
}
