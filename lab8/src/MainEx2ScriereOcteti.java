import java.io.*;
import java.util.Scanner;

import exercitii.Persoana;
import exceptii.ExceptieCustom;

public class MainEx2ScriereOcteti {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueAdding = true;

        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("persoaneOcteti.txt"))) {
            while (continueAdding) {
                try {
                    System.out.println("Introduceti numele:");
                    String nume = scanner.nextLine();
                    System.out.println("Introduceti prenumele:");
                    String prenume = scanner.nextLine();
                    System.out.println("Introduceti varsta:");
                    int varsta = Integer.parseInt(scanner.nextLine());
                    System.out.println("Introduceti suma:");
                    double suma = Double.parseDouble(scanner.nextLine());

                    if (suma > 2000) {
                        throw new ExceptieCustom("Suma nu poate fi mai mare de 2000");
                    }

                    System.out.println("Introduceti valuta:");
                    String valuta = scanner.nextLine();

                    Persoana persoana = new Persoana(nume, prenume, varsta, suma, valuta);
                    writer.writeObject(persoana);

                    System.out.print("Doriti sa adaugati alta persoana? (da/nu): ");
                    String response = scanner.nextLine();
                    if (!response.equalsIgnoreCase("da")) {
                        continueAdding = false;
                    }
                } catch (ExceptieCustom e) {
                    System.out.println(e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Eroare: Varsta si suma trebuie sa fie numere valide.");
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea in fisier: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
