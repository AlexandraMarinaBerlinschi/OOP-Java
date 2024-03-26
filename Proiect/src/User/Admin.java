package User;
import Interfaces.IUser;

public class Admin implements IUser {
    private String username;
    private String password;
    private static final String adminCode = "0000";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Admin(String username, String password, String code) {
        if (adminCode.equals(code)) {
            this.username = username;
            this.password = password;
        } else {
            System.out.println("Cod incorect. Nu poti crea un cont de admin fara codul de acces");
        }
    }

    public void updatePassword(String newPassword) {
        if (ValidPassword(newPassword)) {
            this.password = newPassword;
            System.out.println("Parola a fost setata cu succes");
        } else {
            System.out.println("Parola este invalida");
        }
    }

    private boolean ValidPassword(String password) {
        return password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}");
    }

    public boolean verifyPassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }
}
