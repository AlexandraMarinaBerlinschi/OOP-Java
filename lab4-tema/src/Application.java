import Interfete.BankCard;
import Interfete.CreditCard;
import Interfete.DebitCard;
import Interfete.ShoppingMall;
public class Application {
    public static void main(String[] args) {
        ShoppingMall shoppingMall = new ShoppingMall(new DebitCard("Popescu", 500));
        shoppingMall.achizitie(200);

        shoppingMall = new ShoppingMall(new CreditCard("Popovici"));
        shoppingMall.achizitie(300);
    }
}