import User.Admin;
import User.NormalUser;
import Services.UserService;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static boolean Autentificat = false;

    public static void main(String[] args) {
        while (true) {
            if (!Autentificat) {
                System.out.println("Bine ati venit! Va rugam sa va autentificati");
                System.out.println("1. Inregistrare");
                System.out.println("2. Autentificare");
                System.out.println("3. Iesire");

                String userChoice = scanner.nextLine().trim();
                switch (userChoice) {
                    case "1":
                        registerUser();
                        break;
                    case "2":
                        Autentificat = loginUser();
                        break;
                    case "3":
                        System.out.println("Iesire din sistemul de autentificare.");
                        return;
                    default:
                        System.out.println("Optiune invalida. Va rugam incercati din nou.");
                        break;
                }
            } else {
                userOperations();
            }
        }
    }

    private static void registerUser() {
        System.out.println("Selectati tipul de cont:");
        System.out.println("a. Admin");
        System.out.println("b. User");
        String register = scanner.nextLine().trim().toLowerCase();
        switch (register) {
            case "a":
                createAdmin();
                break;
            case "b":
                createUser();
                break;
            default:
                System.out.println("Selectie invalida. Va rugam alegeti o varianta de mai sus");
        }
    }

    private static void createAdmin() {
        System.out.println("Introduceti username:");
        String username = scanner.nextLine().trim();
        System.out.println("Introduceti parola:");
        String password = scanner.nextLine().trim();
        System.out.println("Introduceti codul de admin:");
        String adminCode = scanner.nextLine().trim();

        if (adminCode.equals("0000")) {
            Admin admin = new Admin(username, password, adminCode);
            userService.addAdmin(admin);
        } else {
            System.out.println("Nu s-a putut crea contul de admin - cod incorect.");
        }
    }

    private static void createUser() {
        System.out.println("Introduceti username:");
        String username = scanner.nextLine().trim();
        System.out.println("Introduceti parola:");
        String password = scanner.nextLine().trim();
        System.out.println("Aveti un cod de redeem? (Da/Nu)");
        String raspuns = scanner.nextLine().trim();
        String redeemCode = null;

        if (raspuns.equalsIgnoreCase("Da")) {
            System.out.println("Introduceti codul de redeem:");
            redeemCode = scanner.nextLine().trim();
        }

        try {
            NormalUser newUser = new NormalUser(username, password, redeemCode);
            boolean inregistrareReusita = userService.addUser(newUser);

            if (inregistrareReusita) {
                System.out.println("Cont de utilizator creat cu succes. Cod unic: " + newUser.getCodUnic());
            } else {
                System.out.println("Inregistrare esuata.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean loginUser() {
        System.out.println("Introduceti username:");
        String username = scanner.nextLine().trim();
        System.out.println("Introduceti parola:");
        String password = scanner.nextLine().trim();

        if (userService.login(username, password)) {
            System.out.println("Autentificare reusita. Bine ati venit, " + username);
            return true;
        } else {
            System.out.println("Autentificare esuata. Verificati username-ul si parola.");
            return false;
        }
    }

    private static void userOperations() {
        System.out.println("Sunteti autentificat. Alegeti o actiune:");
        System.out.println("1. Logout");
        System.out.println("2. Continua cu alte operatii");
        String userChoice = scanner.nextLine().trim();
        if ("1".equals(userChoice)) {
            Autentificat = false;
            System.out.println("V-ati delogat cu succes.");
        } else {
            //trebuie sa revin cu alte operatii aici
        }
    }
}
