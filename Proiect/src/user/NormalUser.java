package user;

import tourism.TouristPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NormalUser extends User {
    private String codUnic;
    private String redeemCode;
    private int referinte;
    private List<TouristPackage> pacheteRezervate;
    private boolean anulareGratuita;
    private int discountProcentaj = 0;

    public NormalUser(String username, String password, String redeemCode) {
        super(username, password);
        this.redeemCode = redeemCode;
        this.codUnic = CodUnic();
        this.referinte = 0;
        pacheteRezervate = new ArrayList<>();
    }


    @Override
    public void updatePassword(String newPassword) {
        if (ValidPassword(newPassword)) {
            this.password = newPassword;
            System.out.println("Parola a fost setata cu succes");
        } else {
            System.out.println("Parola este invalida");
        }
    }

    public static boolean ValidPassword(String password) {
        return password.matches("(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).{6,}");
    }

    public String CodUnic() {
        return UUID.randomUUID().toString();
    }

    public String getCodUnic() {
        return codUnic;
    }

    public void incrementReferinte() {
        referinte++;
    }

    public int getReferinte() {
        return referinte;
    }

    public void rezervaPachet(TouristPackage pachet) {
        pacheteRezervate.add(pachet);
    }

    public List<TouristPackage> getPacheteRezervate() {
        return pacheteRezervate;
    }

    public boolean isAnulareGratuita() {
        return anulareGratuita;
    }

    public void setAnulareGratuita(boolean anulareGratuita) {
        this.anulareGratuita = anulareGratuita;
    }

    public void setDiscountProcentaj(int discountProcentaj) {
        this.discountProcentaj = discountProcentaj;
    }

    public int getDiscountProcentaj() {
        return discountProcentaj;
    }
    public void updateBenefits() {
        int referinte = getReferinte();
        if (referinte >= 5) {
            anulareGratuita = true;
        }
        if (referinte >= 10) {
            discountProcentaj = 10;
        }

    }
}
