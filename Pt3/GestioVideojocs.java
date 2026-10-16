package Pt3;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GestioVideojocs {

    private static final String FITXER = "Pt3/videojocs.dat";

    public static void main(String[] args) {
        ArrayList<Videojoc> cataleg = carregarVideojocs();
        int opcio = 0;

        try (Scanner sc = new Scanner(System.in)) {
            do {
                System.out.println("\n=== MENÚ DE GESTIÓ DE VIDEOJOCS ===");
                System.out.println("1. Afegir videojoc");
                System.out.println("2. Llistar tots els videojocs");
                System.out.println("3. Cercar videojocs per títol");
                System.out.println("4. Actualitzar un videojoc");
                System.out.println("5. Eliminar un videojoc");
                System.out.println("6. Sortir");
                System.out.print("Tria una opció: ");

                try {
                    opcio = sc.nextInt();
                    sc.nextLine(); // Netejar el buffer

                    switch (opcio) {
                        case 1 -> afegirVideojoc(sc, cataleg);
                        case 2 -> llistarVideojocs(cataleg);
                        case 3 -> cercarVideojoc(sc, cataleg);
                        case 4 -> actualitzarVideojoc(sc, cataleg);
                        case 5 -> eliminarVideojoc(sc, cataleg);
                        case 6 -> {
                            desarVideojocs(cataleg);
                            System.out.println("Dades guardades correctament. Sortint del programa...");
                        }
                        default -> System.out.println("Opció incorrecta. Torna-ho a provar.");
                    }
                } catch (Exception e) {
                    System.out.println("Error en l'entrada de dades. Torna a intentar-ho.");
                    sc.nextLine(); // Netejar el buffer en cas d'error
                }
            } while (opcio != 6);
        }
    }

    // --- Mètodes CRUD ---

    private static void afegirVideojoc(Scanner sc, ArrayList<Videojoc> cataleg) {
        System.out.print("Títol: ");
        String titol = sc.nextLine();
        System.out.print("Gènere (ex: acció, rol): ");
        String genere = sc.nextLine();
        System.out.print("Any de llançament: ");
        int any = sc.nextInt();
        sc.nextLine(); // Buffer
        System.out.print("Plataforma (ex: PC, PlayStation): ");
        String plataforma = sc.nextLine();
        System.out.print("Preu: ");
        double preu = sc.nextDouble();
        sc.nextLine(); // Buffer

        cataleg.add(new Videojoc(titol, genere, any, plataforma, preu));
        desarVideojocs(cataleg);
        System.out.println("Videojoc afegit i desat correctament!");
    }

    private static void llistarVideojocs(ArrayList<Videojoc> cataleg) {
        if (cataleg.isEmpty()) {
            System.out.println("El catàleg està buit.");
            return;
        }
        System.out.println("\n--- Llista de Videojocs ---");
        for (Videojoc v : cataleg) {
            System.out.println(v.toString());
        }
    }

    private static void cercarVideojoc(Scanner sc, ArrayList<Videojoc> cataleg) {
        System.out.print("Introdueix el títol (o part del títol) a cercar: ");
        String text = sc.nextLine().toLowerCase();
        boolean trobat = false;

        System.out.println("\n--- Resultats de la cerca ---");
        for (Videojoc v : cataleg) {
            if (v.getTitol().toLowerCase().contains(text)) {
                System.out.println(v.toString());
                trobat = true;
            }
        }
        if (!trobat) System.out.println("No s'han trobat coincidències.");
    }

    private static void actualitzarVideojoc(Scanner sc, ArrayList<Videojoc> cataleg) {
        System.out.print("Introdueix el títol exacte del videojoc a actualitzar: ");
        String titolExacte = sc.nextLine();

        for (Videojoc v : cataleg) {
            if (v.getTitol().equalsIgnoreCase(titolExacte)) {
                System.out.println("Videojoc trobat. Introdueix les noves dades:");

                System.out.print("Nou gènere (actual: " + v.getGenere() + "): ");
                String nouGenere = sc.nextLine();
                if (!nouGenere.isBlank()) v.setGenere(nouGenere);

                System.out.print("Nova plataforma (actual: " + v.getPlataforma() + "): ");
                String novaPlat = sc.nextLine();
                if (!novaPlat.isBlank()) v.setPlataforma(novaPlat);

                System.out.print("Nou preu (actual: " + v.getPreu() + "): ");
                v.setPreu(sc.nextDouble());
                sc.nextLine(); // Buffer

                desarVideojocs(cataleg);
                System.out.println("Videojoc actualitzat correctament.");
                return;
            }
        }
        System.out.println("No s'ha trobat cap videojoc amb aquest títol exacte.");
    }

    private static void eliminarVideojoc(Scanner sc, ArrayList<Videojoc> cataleg) {
        System.out.print("Introdueix el títol exacte del videojoc a eliminar: ");
        String titolExacte = sc.nextLine();

        boolean eliminat = cataleg.removeIf(v -> v.getTitol().equalsIgnoreCase(titolExacte));

        if (eliminat) {
            desarVideojocs(cataleg);
            System.out.println("Videojoc eliminat correctament.");
        } else {
            System.out.println("No s'ha trobat el videojoc.");
        }
    }

    // --- Mètodes de Persistència ---

    @SuppressWarnings("unchecked")
    private static ArrayList<Videojoc> carregarVideojocs() {
        ArrayList<Videojoc> cataleg = new ArrayList<>();
        File fitxer = new File(FITXER);

        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                cataleg = (ArrayList<Videojoc>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error en carregar el fitxer: " + e.getMessage());
            }
        }
        return cataleg;
    }

    private static void desarVideojocs(ArrayList<Videojoc> cataleg) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(cataleg);
        } catch (IOException e) {
            System.out.println("Error en desar les dades: " + e.getMessage());
        }
    }
}