package Interfete;

public class DebitCard implements BankCard {
    private static double spentAmount = 0;
    private String utilizator;
    private double limitAmount;

    public DebitCard(String utilizator, double limitAmount) {
        this.utilizator = utilizator;
        this.limitAmount = limitAmount;
    }

    @Override
    public void doTransaction(double amount) {
        if (amount <= limitAmount) {
            spentAmount += amount;
            System.out.println("Utilizator " + utilizator + " a cheltuit " + amount);
            System.out.println("Suma totala cheltuita cu carduri de debit este " + spentAmount);
        } else {
            System.out.println("Fonduri insuficiente");
        }
    }
}