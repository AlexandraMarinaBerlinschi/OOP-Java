package Imutabile;

public class Application {
    public static void main(String[] args) {
        Shirt shirt = new Shirt("Blue", "M");

        Wardrobe wardrobe = new Wardrobe(5, shirt);
        System.out.println("Mutable Wardrobe: " + wardrobe.getShirt());

        WardrobeImmutable wardrobeImmutable = new WardrobeImmutable(5, shirt);

        shirt.setColor("Red");

        System.out.println("Wardrobe mutable: " + wardrobe.getShirt());

        Shirt shirtImmutable = wardrobeImmutable.getShirt();
        System.out.println("Wardrobe immutable: " + shirtImmutable);
    }
}