package Interfete;

public class CreditCard implements BankCard {
    private static double spentAmount = 0;
    private String utilizator;

    public CreditCard(String utilizator) {
        this.utilizator = utilizator;
    }

    @Override
    public void doTransaction(double amount) {
        spentAmount += amount;
        System.out.println("Utilizator " + utilizator + " a cheltuit " + amount);
        System.out.println("Suma totala cheltuita cu carduri de credit este " + spentAmount);
    }
}