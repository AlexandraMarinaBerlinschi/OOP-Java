package User;
import Interfaces.IUser;
import java.util.UUID;

public class NormalUser implements IUser {
    private String username;
    private String password;
    private String codUnic;
    private String redeemCode;
    private int referinte;

    public NormalUser(String username, String password, String redeemCode) {
        if (!ValidPassword(password)) {
            throw new IllegalArgumentException("Parola este invalida.");
        }
        this.username = username;
        this.password = password;
        this.redeemCode = redeemCode;
        this.codUnic = CodUnic();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean verifyPassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

}
