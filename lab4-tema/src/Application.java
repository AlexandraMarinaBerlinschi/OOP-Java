import Interfete.BankCard;
import Interfete.CreditCard;
import Interfete.DebitCard;
import Interfete.ShoppingMall;
public class Application {
    public static void main(String[] args) {
        ShoppingMall shoppingMall = new ShoppingMall(new DebitCard("Popescu", 500));
        shoppingMall.achizitie(200);

        ShoppingMall shoppingMall1 = new ShoppingMall(new DebitCard("Marinescu", 500));
        shoppingMall1.achizitie(300);

        ShoppingMall shoppingMall2 = new ShoppingMall(new DebitCard("Popa", 500));
        shoppingMall2.achizitie(700);

        shoppingMall = new ShoppingMall(new CreditCard("Popovici"));
        shoppingMall.achizitie(300);

    }
}