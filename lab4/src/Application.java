import Interfete.impl.Cerc;
import Interfete.impl.Patrat;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Raza cercului: ");
        double razaCerc = scanner.nextDouble();
        Cerc cerc = new Cerc(razaCerc);
        System.out.println("Perimetrul cercului este: " + cerc.calculPerimetru());

        System.out.print("Latura patratului: ");
        double laturaPatrat = scanner.nextDouble();
        Patrat patrat = new Patrat(laturaPatrat);
        System.out.println("Perimetrul patratului este: " + patrat.calculPerimetru());

        scanner.close();
    }
}