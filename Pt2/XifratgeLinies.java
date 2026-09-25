import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class XifratgeLinies {

    public static void main(String[] args) {
        int clau = 3;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Introdueix la clau de xifrat (nombre enter, ex: 3): ");
            try {
                clau = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no vàlida. S'utilitzarà la clau per defecte: 3");
            }
        }

        String fitxerEntrada = "Pt2/entrada.txt";
        String fitxerXifrat = "Pt2/xifrat.txt";
        String fitxerDesxifrat = "Pt2/desxifrat.txt";

        System.out.println("\n--- Iniciant procés de xifrat ---");
        xifrarFitxer(fitxerEntrada, fitxerXifrat, clau);

        System.out.println("\n--- Iniciant procés de desxifrat ---");
        desxifrarFitxer(fitxerXifrat, fitxerDesxifrat, clau);
    }

    public static void xifrarFitxer(String origen, String desti, int clau) {
        try (
            BufferedReader br = new BufferedReader(new FileReader(origen));
            BufferedWriter bw = new BufferedWriter(new FileWriter(desti))
        ) {
            String linia;
            int liniesProcessades = 0;

            while ((linia = br.readLine()) != null) {
                String liniaInvertida = new StringBuilder(linia).reverse().toString();
                String liniaXifrada = aplicarCesar(liniaInvertida, clau);

                bw.write(liniaXifrada);
                bw.newLine();
                liniesProcessades++;
            }

            System.out.println("Xifrat completat amb èxit. Línies processades: " + liniesProcessades);
            System.out.println("Fitxer generat: " + desti);

        } catch (FileNotFoundException e) {
            System.err.println("Error: No s'ha trobat el fitxer d'entrada '" + origen + "'.");
        } catch (IOException e) {
            System.err.println("Error de lectura/escriptura durant el xifrat: " + e.getMessage());
        }
    }

    public static void desxifrarFitxer(String origen, String desti, int clau) {
        try (
            BufferedReader br = new BufferedReader(new FileReader(origen));
            BufferedWriter bw = new BufferedWriter(new FileWriter(desti))
        ) {
            String linia;
            int liniesProcessades = 0;

            while ((linia = br.readLine()) != null) {
                String liniaAmbCesarInvers = aplicarCesar(linia, -clau);
                String liniaOriginal = new StringBuilder(liniaAmbCesarInvers).reverse().toString();

                bw.write(liniaOriginal);
                bw.newLine();
                liniesProcessades++;
            }

            System.out.println("Desxifrat completat amb èxit. Línies processades: " + liniesProcessades);
            System.out.println("Fitxer generat: " + desti);

        } catch (FileNotFoundException e) {
            System.err.println("Error: No s'ha trobat el fitxer xifrat '" + origen + "'.");
        } catch (IOException e) {
            System.err.println("Error de lectura/escriptura durant el desxifrat: " + e.getMessage());
        }
    }

    private static String aplicarCesar(String text, int desplaçament) {
        StringBuilder resultat = new StringBuilder();
        for (char c : text.toCharArray()) {
            resultat.append((char) (c + desplaçament));
        }
        return resultat.toString();
    }
}