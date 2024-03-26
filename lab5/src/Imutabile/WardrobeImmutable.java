package Imutabile;

public final class WardrobeImmutable {
    private final int numberOfShirts;
    private final Shirt shirt;

    public WardrobeImmutable(int numberOfShirts, Shirt shirt) {
        this.numberOfShirts = numberOfShirts;
        this.shirt = new Shirt(shirt.getColor(), shirt.getSize());
    }

    public int getNumberOfShirts() {
        return numberOfShirts;
    }

    public Shirt getShirt() {
        return new Shirt(shirt.getColor(), shirt.getSize());
    }
}