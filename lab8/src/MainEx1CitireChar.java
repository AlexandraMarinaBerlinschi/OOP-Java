import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainEx1CitireChar {
    public static void main(String[] args) {
        String filePath = "persoaneChar.txt";
        Map<String, List<String>> persoane = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linie;

            while ((linie = reader.readLine()) != null) {
                String[] parte = linie.split(";");
                if (parte.length >= 5) {
                    String numePrenume = parte[0].trim() + " " + parte[1].trim();
                    persoane.computeIfAbsent(numePrenume, k -> new ArrayList<>()).add(linie);
                }
            }

            for (Map.Entry<String, List<String>> entry : persoane.entrySet()) {
                if (entry.getValue().size() > 1) {
                    System.out.println("Persoane cu numele si prenumele " + entry.getKey() + ":");
                    for (String detalii : entry.getValue()) {
                        System.out.println(detalii);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea din fișier: " + e.getMessage());
        }
    }
}
