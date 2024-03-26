public class Ex2 {
     public static void main(String[] args) {
            String text = "Odata creat un sir de caractere cu continutul sau nu mai poate fi modificat.";

            System.out.println("Lungimea sirului: " + text.length());

            System.out.println("Sirul are doar litere mari si mici: " + text.matches("[a-zA-Z ]+"));

            String[] cuvinte = text.split(" ");
            int middle = cuvinte.length / 2;

            if (cuvinte.length % 2 == 0) {
                System.out.println("Cuvintele de la mijloc: " + cuvinte[middle - 1] + " " + cuvinte[middle]);
            } else {
             System.out.println("Cuvantul de la mijloc: " + cuvinte[middle]);
            }

            StringBuilder reversed = new StringBuilder(text).reverse();
            System.out.println("Stringul inversat: " + reversed);
        }
    }

