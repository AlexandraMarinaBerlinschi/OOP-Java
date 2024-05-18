package services;

import interfaces.IUser;
import tourism.Destination;
import tourism.TouristPackage;
import user.NormalUser;
import user.Admin;
import daoservices.UserRepositoryService;
import audit.LoggingService;

import java.sql.SQLException;
import java.util.*;


public class UserService {
    private Map<String, Admin> admini;
    private Map<String, NormalUser> users;
    private UserRepositoryService userRepositoryService;

    private IUser utilizatorConectat;
    private static Scanner scanner = new Scanner(System.in);
    private TourismService tourismService = new TourismService();

    public UserService(TourismService tourismService) {
        this.admini = new HashMap<>();
        this.users = new HashMap<>();
        this.tourismService = tourismService;
        this.userRepositoryService = new UserRepositoryService();
    }


    public boolean login(String username, String password) {
        Admin admin = userRepositoryService.getAdminByUsername(username);
        NormalUser normalUser = userRepositoryService.getNormalUserByUsername(username);

        if (admin != null && admin.verifyPassword(password)) {
            utilizatorConectat = admin;
            LoggingService.logAction("login", admin.getUsername());
            return true;
        } else if (normalUser != null && normalUser.verifyPassword(password)) {
            utilizatorConectat = normalUser;
            LoggingService.logAction("login", normalUser.getUsername());
            return true;
        }
        return false;
    }
    public IUser getUtilizatorConectat() {
        return utilizatorConectat;
    }

    public void registerUser() {
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

    public boolean createUser() {
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
            if (userRepositoryService.getNormalUserByUsername(username) != null) {
                System.out.println("Utilizatorul cu username-ul " + username + " deja exista in sistem.");
                return false;
            } else if (!NormalUser.isValidPassword(password)) {
                System.out.println("Parola trebuie sa contina cel putin o litera mare, o cifra si un caracter special.");
                return false;
            } else {
                NormalUser newUser = new NormalUser(username, password, redeemCode);
                newUser.incrementReferinte();
                newUser.updateBenefits();
                userRepositoryService.addNormalUser(newUser);
                System.out.println("Cont de utilizator creat cu succes.");
                System.out.println("Codul unic generat pentru utilizator: " + newUser.getCodUnic());
                System.out.println("Numarul de referinte: " + newUser.getReferinte());

                if (redeemCode != null && !redeemCode.isEmpty()) {
                    NormalUser user = applyRedeemCode(redeemCode, newUser);
                    if (user != null) {
                        verificaBeneficii(user);
                    }
                    System.out.println("Codul de redeem a fost aplicat cu succes.");
                }
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public NormalUser applyRedeemCode(String redeemCode, NormalUser newUser) {
        NormalUser user = userRepositoryService.getNormalUserByRedeemCode(redeemCode);
        if (user != null) {
            user.incrementReferinte();
            user.updateBenefits();
            return user;
        }
        return null;
    }

    public boolean createAdmin() {
        System.out.println("Introduceti username:");
        String username = scanner.nextLine().trim();
        System.out.println("Introduceti parola:");
        String password = scanner.nextLine().trim();
        System.out.println("Introduceti codul de admin:");
        String adminCode = scanner.nextLine().trim();

        if (userRepositoryService.getAdminByUsername(username) != null) {
            System.out.println("Adminul cu username-ul " + username + " deja exista in sistem.");
            return false;
        } else {
            if (adminCode.equals("0000")) {
                Admin admin = new Admin(username, password, adminCode);
                userRepositoryService.addAdmin(admin);
                System.out.println("Adminul " + username + " a fost adaugat cu succes.");
                return true;
            } else {
                System.out.println("Nu s-a putut crea contul de admin - cod incorect.");
                return false;
            }
        }
    }

    public IUser loginUser() {
        System.out.println("Introduceti username:");
        String username = scanner.nextLine().trim();
        System.out.println("Introduceti parola:");
        String password = scanner.nextLine().trim();

        if (login(username, password)) {
            System.out.println("Autentificare reusita. Bine ati venit, " + username);
            return getUtilizatorConectat();
        } else {
            System.out.println("Autentificare esuata. Verificati username-ul si parola.");
            return null;
        }
    }

    public String citesteString(String mesaj) {
        System.out.println(mesaj);
        return scanner.nextLine().trim();
    }

    private static float citesteFloat(String mesaj) {
        while (true) {
            try {
                System.out.println(mesaj);
                return Float.parseFloat(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Va rugam introduceti un numar valid.");
            }
        }
    }

    private static int citesteInt(String mesaj) {
        while (true) {
            try {
                System.out.println(mesaj);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Va rugam introduceti un numar intreg valid.");
            }
        }
    }

    public void rezervaPachet(NormalUser normalUser) {
        List<TouristPackage> pachete = tourismService.getPacheteTuristice();
        if (pachete.isEmpty()) {
            System.out.println("Nu exista pachete disponibile pentru rezervare");
            return;
        }
        System.out.println("Selectati pachetul pe care doriti sa il rezervati:");
        for (int i = 0; i < pachete.size(); i++) {
            System.out.println((i + 1) + ". " + pachete.get(i).getNume());
        }

        int choice = citesteInt("Alegerea dumneavoastra:");

        if (choice > 0 && choice <= pachete.size()) {
            TouristPackage pachetRezervat = pachete.get(choice - 1);

            try {
                List<TouristPackage> rezervari = userRepositoryService.getReservations(normalUser.getUsername());
                for (TouristPackage rezervare : rezervari) {
                    if (rezervare.getId() == pachetRezervat.getId()) {
                        System.out.println("Ati rezervat deja acest pachet. Nu puteti face o rezervare dublă.");
                        return;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Eroare la verificarea rezervarilor existente.");
                return;
            }

            verificaPromotie(normalUser, pachetRezervat);
            verificaBeneficii(normalUser);
            normalUser.rezervaPachet(pachetRezervat);
            try {
                userRepositoryService.saveReservation(normalUser.getUsername(), pachetRezervat.getId());
                System.out.println("Pachetul " + pachetRezervat.getNume() + " a fost rezervat cu succes");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Eroare la salvarea rezervarii.");
            }
        } else {
            System.out.println("Alegere invalida. Pachetul nu a fost rezervat");
        }
    }



    private void verificaPromotie(NormalUser user, TouristPackage pachet) {
        int numarPacheteRezervate = user.getPacheteRezervate().size();
        if (numarPacheteRezervate >= 5) {
            double discount = 10.0;
            pachet.setPret(pachet.getPret() * (1 - discount / 100));
            System.out.println("O reducere de " + discount + "% a fost aplicata pentru pachetul " + pachet.getNume());
        }
    }

    private void verificaBeneficii(NormalUser user) {
        int numarReferinte = user.getReferinte();
        if (numarReferinte >= 3) {
            user.setAnulareGratuita(true);
            System.out.println("Beneficiul de anulare gratuita a fost acordat pentru " + user.getUsername());
        }
        if (numarReferinte >= 5) {
            user.setDiscountProcentaj(10); // Setează reducerea la 10%
            System.out.println("O reducere de 10% a fost acordată pentru " + user.getUsername());
        }
    }

    public void raportCont(NormalUser normalUser) {
        System.out.println("===== Raport Cont =====");
        System.out.println("Username: " + normalUser.getUsername());

        try {
            List<TouristPackage> rezervari = userRepositoryService.getReservations(normalUser.getUsername());
            System.out.println("Numarul de pachete turistice rezervate: " + rezervari.size());

            if (!rezervari.isEmpty()) {
                System.out.println("Detalii Pachete Rezervate:");
                for (TouristPackage pachet : rezervari) {
                    System.out.println("- Nume Pachet: " + pachet.getNume());
                    System.out.println("  Destinatie: " + pachet.getDestinatie());
                    System.out.println("  Durata: " + pachet.getDurata());
                    System.out.println("  Pret: " + pachet.getPret());
                }
            } else {
                System.out.println("Nu aveti rezervari in acest moment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void raportVanzari() {
        double totalVanzari = 0;
        try {
            List<NormalUser> normalUsers = userRepositoryService.getAllNormalUsers();
            for (NormalUser user : normalUsers) {
                List<TouristPackage> rezervari = userRepositoryService.getReservations(user.getUsername());
                for (TouristPackage pachet : rezervari) {
                    totalVanzari += pachet.getPret();
                }
            }
            System.out.println("Totalul vanzarilor de pachete turistice este: " + totalVanzari);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean removeUser(String username, String password) {
        if (utilizatorConectat instanceof NormalUser) {
            NormalUser normalUser = (NormalUser) utilizatorConectat;
            if (normalUser.getUsername().equals(username) && normalUser.verifyPassword(password)) {
                if (userRepositoryService.removeNormalUser(username)) {
                    System.out.println("Utilizatorul " + username + " a fost sters cu succes.");
                    utilizatorConectat = null;
                    return true;
                }
            } else {
                System.out.println("Nu aveti permisiunea sa stergeti acest utilizator.");
            }
        } else {
            System.out.println("Nu aveti privilegiile necesare pentru aceasta acțiune.");
        }
        return false;
    }

    public boolean removeAdmin(String username, String password) {
        if (utilizatorConectat instanceof Admin) {
            Admin admin = (Admin) utilizatorConectat;
            if (admin.getUsername().equals(username) && admin.verifyPassword(password)) {
                if (userRepositoryService.removeAdmin(username)) {
                    System.out.println("Contul adminului " + username + " a fost șters cu succes.");
                    utilizatorConectat = null;
                    return true;
                } else {
                    System.out.println("Contul adminului " + username + " nu exista în sistem.");
                }
            } else {
                System.out.println("Nu aveti permisiunea sau parola corecta pentru a sterge acest cont de admin.");
            }
        } else {
            System.out.println("Nu aveți privilegiile necesare pentru aceasta actiune.");
        }
        return false;
    }

    public boolean updateNormalUser(String username, String oldPassword, String newPassword) {
        if (utilizatorConectat instanceof NormalUser) {
            NormalUser normalUser = (NormalUser) utilizatorConectat;
            if (normalUser.getUsername().equals(username) && normalUser.verifyPassword(oldPassword)) {
                if (!NormalUser.isValidPassword(newPassword)) {
                    System.out.println("Parola noua este invalida.");
                    return false;
                }
                normalUser.updatePassword(newPassword);
                if (userRepositoryService.updateNormalUser(username, normalUser)) {
                    System.out.println("Contul utilizatorului " + username + " a fost actualizat cu succes.");
                    return true;
                } else {
                    System.out.println("Eroare la actualizarea contului utilizatorului.");
                    return false;
                }
            } else {
                System.out.println("Nu aveti permisiunea sau parola corecta pentru a actualiza acest cont de utilizator normal.");
            }
        } else {
            System.out.println("Nu aveti privilegiile necesare pentru aceasta acțiune.");
        }
        return false;
    }

    public boolean updateAdmin(String username, String oldPassword, String newPassword) {
        if (utilizatorConectat instanceof Admin) {
            Admin admin = (Admin) utilizatorConectat;
            if (admin.getUsername().equals(username) && admin.verifyPassword(oldPassword)) {
                if (!Admin.ValidPassword(newPassword)) {
                    System.out.println("Parola noua este invalida (trebuie sa contina un caracter mic, un caracter mare si un caracter special)");
                    return false;
                }
                admin.updatePassword(newPassword);
                if (userRepositoryService.updateAdmin(username, admin)) {
                    System.out.println("Contul adminului " + username + " a fost actualizat cu succes.");
                    return true;
                } else {
                    System.out.println("Eroare la actualizarea contului adminului.");
                    return false;
                }
            } else {
                System.out.println("Nu aveti permisiunea sau parola corecta pentru a actualiza acest cont de admin.");
            }
        } else {
            System.out.println("Nu aveți privilegiile necesare pentru aceasta acțiune.");
        }
        return false;
    }

}
