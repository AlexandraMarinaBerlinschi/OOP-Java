import dao.ReviewDao;
import reviewSystem.Review;
import services.*;
import daoservices.ReviewRepositoryService;
import user.Admin;
import user.NormalUser;

import java.util.List;
import java.util.Scanner;
import interfaces.IUser;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static TourismService tourismService = new TourismService();
    private static UserService userService = new UserService(tourismService);
    private static ReviewRepositoryService reviewRepositoryService = new ReviewRepositoryService(new ReviewDao());
    private static ReviewService reviewService = new ReviewService(tourismService, reviewRepositoryService);
    private static boolean Autentificat = false;

    public static void main(String[] args) {
        while (true) {
            System.out.println("Bine ati venit! Va rugam sa va autentificati");
            System.out.println("1. Inregistrare");
            System.out.println("2. Autentificare");
            System.out.println("3. Iesire");

            String userChoice = scanner.nextLine().trim();
            switch (userChoice) {
                case "1":
                    userService.registerUser();
                    break;
                case "2":
                    IUser user = userService.loginUser();
                    if (user != null) {
                        userOperations(user, userService, reviewService);
                    }
                    break;
                case "3":
                    System.out.println("Iesire din sistemul de autentificare.");
                    return;
                default:
                    System.out.println("Optiune invalida. Va rugam incercati din nou.");
                    break;
            }
        }
    }

    private static void userOperations(IUser user, UserService userService, ReviewService reviewService) {
        System.out.println("Sunteti autentificat! Alegeti o actiune:");
        System.out.println("1. Logout");
        System.out.println("2. Continua cu alte operatii");
        String userChoice = scanner.nextLine().trim();

        if ("1".equals(userChoice)) {
            System.out.println("V-ati delogat cu succes.");
        } else if ("2".equals(userChoice)) {
            if (user instanceof Admin) {
                adminOperations((Admin) user, userService);
            } else if (user instanceof NormalUser) {
                normalUserOperations((NormalUser) user, userService, reviewService);
            }
        }
    }


        private static void adminOperations(Admin admin, UserService userService) {
        while (true) {
            System.out.println("1.Afisare pachete turistice");
            System.out.println("2.Adauga pachet turistic");
            System.out.println("3.Sterge pachet turistic");
            System.out.println("4.Actualizare pachet turistic");
            System.out.println("5.Filtrare pachete turistice");
            System.out.println("6.Raport vanzari");
            System.out.println("7.Sterge cont");
            System.out.println("8.Actualizeaza parola");
            String adminChoice = scanner.nextLine().trim();
            switch (adminChoice) {
                case "1":
                    tourismService.afisarePachete();
                    break;
                case "2":
                    tourismService.adaugaPachet(admin);
                    break;
                case "3":
                    tourismService.stergePachet(admin);
                    break;
                case "4":
                    tourismService.actualizeazaPachet(admin);
                    break;
                case "5":
                    tourismService.filtrarePachete();
                    break;
                case "6":
                    userService.raportVanzari();
                    break;
                case "7":
                    removeAdmin(admin, userService);
                    break;
                case "8":
                    updateAdmin(admin, userService);
                    break;
            }
            System.out.println("Doriti sa efectuati alta actiune? (Da/Nu)");
            if(scanner.nextLine().trim().equalsIgnoreCase("Nu")) {
                break;
            }
        }
    }

    private static void normalUserOperations(NormalUser normalUser, UserService userService, ReviewService reviewService) {
        while (true) {
            System.out.println("1. Afisare pachete turistice");
            System.out.println("2. Filtrare pachete turistice");
            System.out.println("3. Cautare pachet");
            System.out.println("4. Rezerva pachet");
            System.out.println("5. Raport cont");
            System.out.println("6. Adauga recenzie");
            System.out.println("7. Sterge recenzie");
            System.out.println("8. Actualizeaza recenzie");
            System.out.println("9. Afiseaza toate recenziile tale");
            System.out.println("10.Sterge cont");
            System.out.println("11.Actualizeaza cont");
            System.out.println("12. Logout");
            String normalUserChoice = scanner.nextLine().trim();
            switch (normalUserChoice) {
                case "1":
                    tourismService.afisarePachete();
                    break;
                case "2":
                    tourismService.filtrarePachete();
                    break;
                case "3":
                    tourismService.cautarePachet();
                    break;
                case "4":
                    userService.rezervaPachet(normalUser);
                    break;
                case "5":
                    userService.raportCont(normalUser);
                    break;
                case "6":
                    reviewService.adaugaRecenzie(normalUser);
                    break;
                case "7":
                    reviewService.stergeRecenzie(normalUser);
                    break;
                case "8":
                    reviewService.actualizeazaRecenzie(normalUser);
                    break;
                case "9":
                    List<Review> reviews = reviewRepositoryService.getReviewsByUser(normalUser.getUsername());
                    reviewService.afisareRecenzii(reviews);
                    break;
                case "10":
                    removeUser(normalUser, userService);
                    break;
                case "11":
                    updateUser(normalUser, userService);
                case "12":
                    Autentificat = false;
                    System.out.println("V-ati delogat cu succes.");
                    return;
            }
        }
    }
    private static void removeUser(NormalUser normalUser, UserService userService) {
        System.out.println("Introduceti username-ul dvs. pentru a confirma stergerea contului:");
        String username = scanner.nextLine().trim();
        System.out.println("Introduceti parola dvs. pentru a confirma stergerea contului:");
        String password = scanner.nextLine().trim();
        userService.removeUser(username, password);
    }

    private static void removeAdmin(Admin admin, UserService userService) {
        System.out.println("Introduceti parola adminului pentru a confirma stergerea: ");
        String password = scanner.nextLine().trim();
        userService.removeAdmin(admin.getUsername(),password);
    }

    private static void updateAdmin(Admin admin, UserService userService) {
        System.out.println("Introduceti parola veche:");
        String oldPassword = scanner.nextLine().trim();
        System.out.println("Introduceti noua parola:");
        String newPassword = scanner.nextLine().trim();
        userService.updateAdmin(admin.getUsername(), oldPassword, newPassword);
    }

    private static void updateUser(NormalUser normalUser, UserService userService)
    {
        System.out.println("Introduceti parola veche:");
        String oldPassword = scanner.nextLine().trim();
        System.out.println("Introduceți noua parola:");
        String newPassword = scanner.nextLine().trim();
        userService.updateNormalUser(normalUser.getUsername(), oldPassword, newPassword);

    }

}
