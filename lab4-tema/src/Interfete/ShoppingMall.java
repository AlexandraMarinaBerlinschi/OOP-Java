package Interfete;


public class ShoppingMall {
    private BankCard bankCard;

    public ShoppingMall(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    public void achizitie(double amount) {
        bankCard.doTransaction(amount);
    }
}