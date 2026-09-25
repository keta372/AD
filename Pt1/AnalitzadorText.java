import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalitzadorText {

    public static void main(String[] args) {
        String nomFitxer = "Pt1/text.txt";

        int numCaracters = 0;
        int numLinies = 0;
        int numParaules = 0;
        boolean dinsParaula = false;

        int[] frequencia = new int[65536];

        int c;
        int darrerCaracter = -1;
        boolean teContingut = false;

        try (FileReader fr = new FileReader(nomFitxer)) {

            while ((c = fr.read()) != -1) {
                char caracter = (char) c;
                teContingut = true;
                darrerCaracter = c;

                // 1. Comptar caràcters (excloent salts de línia \n i \r)
                if (caracter != '\n' && caracter != '\r') {
                    numCaracters++;
                }

                // 2. Comptar línies
                if (caracter == '\n') {
                    numLinies++;
                }

                // 3. Comptar paraules i freqüència de caràcters
                boolean esSeparador = (caracter == ' ' || caracter == '\t' || caracter == '\n' || caracter == '\r');

                if (!esSeparador) {
                    if (!dinsParaula) {
                        dinsParaula = true;
                        numParaules++;
                    }
                    frequencia[c]++;
                } else {
                    dinsParaula = false;
                }
            }

            // Gestionar la darrera línia si el fitxer no acaba en \n
            if (teContingut && darrerCaracter != '\n') {
                numLinies++;
            }

            // Buscar el caràcter més repetit
            int maxFrequencia = 0;
            char caracterMesRepetit = ' ';
            boolean trobat = false;

            for (int i = 0; i < frequencia.length; i++) {
                if (frequencia[i] > maxFrequencia) {
                    maxFrequencia = frequencia[i];
                    caracterMesRepetit = (char) i;
                    trobat = true;
                }
            }

            // Resultats per pantalla
            System.out.println("Nombre de caràcters: " + numCaracters);
            System.out.println("Nombre de línies: " + numLinies);
            System.out.println("Nombre de paraules: " + numParaules);

            if (trobat) {
                System.out.println("Caràcter més repetit: " + caracterMesRepetit);
            } else {
                System.out.println("Caràcter més repetit: Cap caràcter vàlid trobat.");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: El fitxer '" + nomFitxer + "' no s'ha trobat.");
        } catch (SecurityException e) {
            System.err.println("Error: No es tenen permisos per llegir el fitxer.");
        } catch (IOException e) {
            System.err.println("Error durant la lectura del fitxer: " + e.getMessage());
        }
    }
}