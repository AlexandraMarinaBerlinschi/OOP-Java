import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import exceptii.ExceptieCustom;

public class MainEx1ScriereChar {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean ok = true;

        while (ok) {
            try {
                System.out.print("Introduceti numele: ");
                String nume = scanner.nextLine();
                System.out.print("Introduceti prenumele: ");
                String prenume = scanner.nextLine();
                System.out.print("Introduceti varsta: ");
                int varsta = Integer.parseInt(scanner.nextLine());
                System.out.print("Introduceti suma: ");
                int suma = Integer.parseInt(scanner.nextLine());
                System.out.print("Introduceti valuta: ");
                String valuta = scanner.nextLine();

                if (suma > 2000) {
                    throw new ExceptieCustom("Suma nu poate fi mai mare de 2000");
                }

                scrieFisier(nume, prenume, varsta, suma, valuta);

                System.out.print("Doriti sa adaugati alta persoana?(da/nu):");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("da")) {
                    ok = false;
                }
            } catch (ExceptieCustom e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("A aparut o eroare la scrierea in fisier: " + e.getMessage());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Eroare: Varsta si suma trebuie sa fie numere valide.");
            }
        }
        scanner.close();
        System.out.println("Procesul de introducere a datelor s-a incheiat.");
    }

    private static void scrieFisier(String nume, String prenume, int varsta, int suma, String valuta) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("persoaneChar.txt", true))) {
            writer.write(String.format("%s;%s;%d;%d;%s%n", nume, prenume, varsta, suma, valuta));
        }
    }
}
