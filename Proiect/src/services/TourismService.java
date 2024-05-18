package services;

import daoservices.TourismRepositoryService;
import tourism.TouristPackage;
import tourism.Destination;
import user.Admin;
import java.util.List;
import java.util.Scanner;
import dao.TouristPackageDao;
import database.DataBaseConnection;
import exceptions.PackageNotFoundException;
import java.sql.Connection;

public class TourismService {
    private List<TouristPackage> pacheteTuristice;
    private static Scanner scanner = new Scanner(System.in);
    private final TourismRepositoryService repositoryService;
    private final TouristPackageDao packageDao;

    public TourismService() {
        Connection connection = DataBaseConnection.getConnection();
        this.repositoryService = new TourismRepositoryService();
        this.packageDao = new TouristPackageDao();
        this.packageDao.addPackage(null, connection);
    }

    public void afisareTouristPackage(TouristPackage pachet) {
        System.out.println(pachet.getNume());
    }

    public List<TouristPackage> getPacheteTuristice() {
        return repositoryService.getAllPackages();
    }

    public void adaugaPachetTuristic(TouristPackage pachet) {
        repositoryService.addPackage(pachet);
        pacheteTuristice = repositoryService.getAllPackages();
    }

    public void removeTouristPackage(TouristPackage pachet) {
        repositoryService.deletePackage(pachet.getId());
        pacheteTuristice = repositoryService.getAllPackages();
    }

    public void updateTouristPackage(TouristPackage updatedPachet) {
        repositoryService.updatePackage(updatedPachet);
    }

    public List<TouristPackage> cautareNume(String nume) {
        return repositoryService.searchByName(nume);
    }

    public List<TouristPackage> cautareDestinatie(String destinatie) {
        return repositoryService.searchByDestination(destinatie);
    }

    public void adaugaPachet(Admin admin) {
        int pachetId = citesteInt("Introduceti ID-ul pachetului turistic:");

        String numePachet = citesteString("Numele pachetului:");
        String numeDestinatie = citesteString("Destinatie:");
        float pret = citesteFloat("Pretul pachetului:");
        float rating = citesteFloat("Ratingul pachetului:");
        int nrPersoane = citesteInt("Numarul de persoane disponibil pentru pachet:");
        String durata = citesteString("Durata disponibila in cadrul pachetului (nopti):");

        Destination destinatie = new Destination(numeDestinatie);
        int destinatieId = repositoryService.addDestination(destinatie);
        if (destinatieId == -1) {
            System.out.println("Eroare la adaugarea destinației in baza de date.");
            return;
        }
        destinatie.setId(destinatieId);

        TouristPackage pachet = new TouristPackage(numePachet, pret, durata, rating, destinatie, nrPersoane);
        pachet.setId(pachetId);

        admin.addTouristPackage(pachet);
        System.out.println("Pachetul " + pachet.getNume() + " a fost adaugat cu succes cu ID-ul: " + pachetId);
    }

    public void detaliiPachet(TouristPackage pachet) {
        System.out.println("ID: " + pachet.getId());
        System.out.println("Nume: " + pachet.getNume());
        System.out.println("Destinatie: " + pachet.getDestinatie());
        System.out.println("Durata: " + pachet.getDurata());
        System.out.println("Pret: " + pachet.getPret());
        System.out.println("Rating: " + pachet.getRating());
        System.out.println("Numar de persoane disponibil: " + pachet.getNrPersoane());
    }

    public void filtrarePachete() {
        System.out.println("Alegeți modul de filtrare:");
        System.out.println("1. Filtrare dupa preț");
        System.out.println("2. Filtrare dupa rating");
        int optiune = citesteInt("Alegerea dvs:");

        List<TouristPackage> pacheteFiltrate;
        switch (optiune) {
            case 1:
                float minPrice = citesteFloat("Introduceti pretul minim:");
                float maxPrice = citesteFloat("Introduceti pretul maxim:");
                pacheteFiltrate = repositoryService.filterPackagesByPrice(minPrice, maxPrice);
                break;
            case 2:
                float minRating = citesteFloat("Introduceti ratingul minim:");
                float maxRating = citesteFloat("Introduceti ratingul maxim:");
                pacheteFiltrate = repositoryService.filterPackagesByRating(minRating, maxRating);
                break;
            default:
                System.out.println("Optiune invalida.");
                return;
        }

        if (pacheteFiltrate.isEmpty()) {
            System.out.println("Nu exista pachete turistice care sa corespunda criteriilor selectate.");
        } else {
            pacheteFiltrate.forEach(pachet -> detaliiPachet(pachet));
        }
    }

    public void afisarePachete() {
        List<TouristPackage> pachete = repositoryService.getAllPackages();
        if (pachete.isEmpty()) {
            System.out.println("Nu exista pachete turistice disponibile");
            return;
        }

        System.out.println("Pachete turistice disponibile:");
        for (int i = 0; i < pachete.size(); i++) {
            System.out.println((i + 1) + ". " + pachete.get(i).getNume());
        }

        System.out.println("Doriti detalii suplimentare despre un pachet? Introduceti ID-ul pachetului sau 0 pentru a continua.");
        int alegere = citesteInt("Alegerea dvs:");

        if (alegere != 0) {
            TouristPackage pachetSelectat = repositoryService.getPackageById(alegere);
            if (pachetSelectat != null) {
                detaliiPachet(pachetSelectat);
            } else {
                System.out.println("ID-ul introdus nu corespunde niciunui pachet turistic.");
            }
        }
    }

    public void stergePachet(Admin admin) {
        System.out.println("Introduceti ID-ul pachetului turistic pe care vreti sa il stergeti:");
        int pachetId = citesteInt("ID:");

        TouristPackage pachetDeSters = repositoryService.getPackageById(pachetId);
        if (pachetDeSters == null) {
            System.out.println("Nu exista niciun pachet turistic cu ID-ul " + pachetId);
            return;
        }

        if (repositoryService.deletePackage(pachetId)) {
            admin.removeTouristPackage(pachetDeSters);
            System.out.println("Pachetul turistic \"" + pachetDeSters.getNume() + "\" a fost sters cu succes");
        } else {
            System.out.println("Stergerea pachetului a esuat");
        }
    }

    public void actualizeazaPachet(Admin admin) {
        System.out.println("Introduceti ID-ul pachetului turistic pe care vreti sa il actualizati:");
        int pachetId = citesteInt("ID:");

        TouristPackage pachetDeActualizat = repositoryService.getPackageById(pachetId);
        if (pachetDeActualizat == null) {
            System.out.println("Nu exista niciun pachet turistic cu ID-ul " + pachetId);
            return;
        }

        System.out.println("Introduceti noile detalii ale pachetului:");
        String numeNou = citesteString("Noul nume (apasati Enter pentru a ramane neschimbat):");
        if (!numeNou.isEmpty()) {
            pachetDeActualizat.setNume(numeNou);
        }

        String destinatieNoua = citesteString("Noua destinatie (apasati Enter pentru a ramane neschimbata):");
        if (!destinatieNoua.isEmpty()) {
            Destination nouaDestinatie = new Destination(destinatieNoua);
            int destinatieId = repositoryService.addDestination(nouaDestinatie);
            if (destinatieId != -1) {
                nouaDestinatie.setId(destinatieId);
                pachetDeActualizat.setDestinatie(nouaDestinatie);
            } else {
                System.out.println("Eroare la adaugarea destinației in baza de date.");
            }
        }

        float pretNou = citesteFloat("Noul pret (introduceti 0 pentru a ramane neschimbat):");
        if (pretNou != 0) {
            pachetDeActualizat.setPret(pretNou);
        }

        int durataNoua = citesteInt("Noua durata in zile (introduceti 0 pentru a ramane neschimbata):");
        if (durataNoua != 0) {
            pachetDeActualizat.setDurata(String.valueOf(durataNoua));
        }

        if (repositoryService.updatePackage(pachetDeActualizat)) {
            System.out.println("Pachetul turistic \"" + pachetDeActualizat.getNume() + "\" a fost actualizat cu succes");
        } else {
            System.out.println("Actualizarea pachetului a esuat");
        }

    }

    public void actualizarePachetMeniu(TouristPackage updatePacket) {
        System.out.println("Selectati numarul atributului pe care vreti sa il actualizati:");
        System.out.println("1. Nume");
        System.out.println("2. Pret");
        System.out.println("3. Durata");
        System.out.println("4. Rating");
        System.out.println("5. Destinatie");
        System.out.println("6. Numarul de Persoane");
        int optiune = citesteInt("Alegerea dvs:");
        switch (optiune) {
            case 1:
                updatePacket.setNume(citesteString("Introduceti noul nume al pachetului:"));
                break;
            case 2:
                updatePacket.setPret(citesteFloat("Introduceti noul pret al pachetului:"));
                break;
            case 3:
                updatePacket.setDurata(citesteString("Introduceti noua durata a pachetului (nopti):"));
                break;
            case 4:
                updatePacket.setRating(citesteFloat("Introduceti noul rating al pachetului:"));
                break;
            case 5:
                String numeDestinatie = citesteString("Introduceti noua destinatie a pachetului:");
                updatePacket.setDestinatie(new Destination(numeDestinatie));
                break;
            case 6:
                updatePacket.setNrPersoane(citesteInt("Introduceti noul numar de persoane pentru pachet:"));
                break;
            default:
                System.out.println("Optiune invalida, va rugam sa alegeti din nou din lista de mai sus");
        }
        repositoryService.updatePackage(updatePacket);
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

    public void cautarePachet() {
        System.out.println("Cautati pachet dupa:");
        System.out.println("1.Nume");
        System.out.println("2.Destinatie");
        int optiune = citesteInt("Alegeti o optiune din cele de mai sus");
        List<TouristPackage> rezultat;
        switch (optiune) {
            case 1:
                String nume = citesteString("Introduceti numele:");
                rezultat = cautareNume(nume);
                break;
            case 2:
                String destinatie = citesteString("Introduceti destinatia:");
                rezultat = cautareDestinatie(destinatie);
                break;
            default:
                System.out.println("Optiune invalida. Va rugam alegeti din cele mentionate mai sus");
                return;
        }
        if (rezultat.isEmpty()) {
            System.out.println("Nu avem deocamdata pachete disponibile");
            return;
        }
        for (int i = 0; i < rezultat.size(); i++) {
            System.out.println((i + 1) + ". " + rezultat.get(i).getNume());
        }
        int selectie = citesteInt("Selectati un pachet pentru detalii");
        if (selectie > 0 && selectie <= rezultat.size()) {
            detaliiPachet(rezultat.get(selectie - 1));
        }
    }
}