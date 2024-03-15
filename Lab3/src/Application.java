import model.Person;
import model.Student;
import model.Profesor;
import java.util.Scanner;
import service.StorageService;

public class Application {

    private static Scanner scanner = new Scanner(System.in);
    private static String operatie;
    private static StorageService storageService = new StorageService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Introduceti operatia (create, read, update, delete, quit) : ");
            operatie = scanner.nextLine().trim().toLowerCase();

            switch (operatie) {
                case "create":
                    createOperatie();
                    break;
                case "read":
                    readOperatie();
                    break;
                case "update":
                    updateOperatie();
                    break;
                case "delete":
                    deleteOperatie();
                    break;
                case "quit":
                    System.out.println("Aplicatia se inchide");
                    return;
                default:
                    System.out.println("Operatie invalida. Va rugam alegeti o operatie din cele enumerate de mai sus");
            }
        }
    }

    private static void createOperatie() {
        System.out.println("Introduceti tipul persoanei (student/profesor): ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.println("Introduceti numele : ");
        String Name = scanner.nextLine().trim();

        System.out.println("Introduceti numarul de telefon : ");
        String Phone_Number = scanner.nextLine().trim();

        System.out.println("Introduceti adresa de email : ");
        String Email_Address = scanner.nextLine().trim().toLowerCase();

        if (type.equals("student")) {
            System.out.println("Introduceti numarul studentului: ");
            String student_number = scanner.nextLine().trim().toLowerCase();

            System.out.println("Introduceti average mark : ");
            float average_mark = Float.parseFloat(scanner.nextLine().trim());

            System.out.println("Introduceti clasa : ");
            int clasa = Integer.parseInt(scanner.nextLine().trim());

            Student student = new Student(Name, Phone_Number, Email_Address, student_number, average_mark, clasa);
            if (storageService.addStudent(student)) {
                System.out.println("Student adaugat cu succes");
            } else {
                System.out.println("Studentul exista deja");
            }
        } else if (type.equals("profesor")) {
            System.out.println("Introduceti cursul profesorului : ");
            String course = scanner.nextLine().trim().toLowerCase();

            System.out.println("Introduceti anul : ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            Profesor profesor = new Profesor(Name, Phone_Number, Email_Address, course, year);
            if (storageService.addProfesor(profesor)) {
                System.out.println("Profesor adaugat cu succes");
            } else {
                System.out.println("Profesorul exista deja");
            }
        } else {
            System.out.println("Tipul introdus este invalid");
        }
    }

    private static void readOperatie() {
        System.out.println("Introduceti numele:");
        String Name = scanner.nextLine().trim();

        Person person = storageService.findPerson(Name);
        if (person != null) {
            System.out.println(person.toString());
        } else {
            System.out.println("Persoana nu exista");
        }
    }

    private static void updateOperatie() {
        System.out.println("Executie opertia de update : ");
        System.out.println("Introduceti numele : ");
        String Name = scanner.nextLine().trim();

        System.out.println("Introduceti Emailul : ");
        String Email_Address = scanner.nextLine().trim().toLowerCase();

        System.out.println("Introduceti numarul de telefon : ");
        String Phone_Number = scanner.nextLine().trim();

        Person person = storageService.findPerson(Name);
        if (person != null) {
            if (storageService.updatePerson(person, Email_Address, Phone_Number)) {
                System.out.println("Update-ul s-a efectuat cu succes");
            } else {
                System.out.println("Eroare la efectuarea update-ului");
            }
        } else {
            System.out.println("Persoana nu a fost gasita");
        }
    }

    private static void deleteOperatie() {
        System.out.println("Executare operatia de delete");
        System.out.println("Introduceti numele: ");
        String Name = scanner.nextLine().trim();

        Person person = storageService.findPerson(Name);
        if (person != null) {
            if (storageService.deletePerson(person)) {
                System.out.println("Persoana a fost stearsa cu succes");
            } else {
                System.out.println("Persoana nu a fost stearsa");
            }
        } else {
            System.out.println("Persoana nu a fost gasita");
        }
    }
}
