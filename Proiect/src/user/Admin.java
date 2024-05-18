package user;

import tourism.TouristPackage;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private static final String adminCode = "0000";
    private List<TouristPackage> pacheteTuristice;

    public Admin(String username, String password, String code) {
        super(username, password);
        if (adminCode.equals(code)) {
            this.pacheteTuristice = new ArrayList<>();
        } else {
            System.out.println("Cod incorect. Nu poti crea un cont de admin fara codul de acces");
        }
    }

    public Admin(String username, String password) {
        super(username, password);
        this.pacheteTuristice = new ArrayList<>();
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
        return password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}");
    }

    public void updateTouristPackage(TouristPackage updatedPackage) {
        for (int i = 0; i < pacheteTuristice.size(); i++) {
            if (pacheteTuristice.get(i).getId() == updatedPackage.getId()) {
                pacheteTuristice.set(i, updatedPackage);
                break;
            }
        }
    }

    public void addTouristPackage(TouristPackage pachet) {
        pacheteTuristice.add(pachet);
    }

    public void removeTouristPackage(TouristPackage pachet) {
        pacheteTuristice.remove(pachet);
    }

    public String getPassword() {
        return password;
    }
}
