package user;

import tourism.TouristPackage;
import daoservices.UserRepositoryService;

import java.sql.SQLException;
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
    private UserRepositoryService userRepositoryService;


    public NormalUser(String username, String password, String redeemCode) {
        super(username, password);
        this.redeemCode = redeemCode;
        this.codUnic = generateCodUnic();
        this.referinte = 0;
        this.pacheteRezervate = new ArrayList<>();
        this.userRepositoryService = new UserRepositoryService();
    }


    @Override
    public void updatePassword(String newPassword) {
        if (isValidPassword(newPassword)) {
            this.password = newPassword;
            System.out.println("Parola a fost setata cu succes");
        } else {
            System.out.println("Parola este invalida");
        }
    }

    public void savePachetRezervat(TouristPackage pachet) {
        try {
            userRepositoryService.saveReservation(this.getUsername(), pachet.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadPacheteRezervate() {
        try {
            List<TouristPackage> pacheteRezervate = userRepositoryService.getReservations(this.getUsername());
            this.pacheteRezervate.addAll(pacheteRezervate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidPassword(String password) {
        return password.matches("(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).{6,}");
    }

    private String generateCodUnic() {
        return UUID.randomUUID().toString();
    }

    public String getCodUnic() {
        return codUnic;
    }

    public void setCodUnic(String codUnic) {
        this.codUnic = codUnic;
    }

    public String getRedeemCode() {
        return redeemCode;
    }

    public void incrementReferinte() {
        referinte++;
    }

    public int getReferinte() {
        return referinte;
    }

    public void setReferinte(int referinte) {
        this.referinte = referinte;
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

    public int getDiscountProcentaj() {
        return discountProcentaj;
    }

    public void setDiscountProcentaj(int discountProcentaj) {
        this.discountProcentaj = discountProcentaj;
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

    public String getPassword() {
        return password;
    }
}
