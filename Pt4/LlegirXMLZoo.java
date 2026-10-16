package Pt4;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LlegirXMLZoo {

    private static final String FITXER_XML = "Pt4/zoo.xml";

    public static void main(String[] args) {
        File fitxer = new File(FITXER_XML);

        if (!fitxer.exists()) {
            System.out.println("El fitxer " + FITXER_XML + " no existeix. Executa primer CrearXMLZoo.java.");
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fitxer);

            doc.getDocumentElement().normalize();

            NodeList llistaAnimals = doc.getElementsByTagName("animal");

            // 1. Mostrar tots els animals
            System.out.println("=== LLISTAT DE TOTS ELS ANIMALS DEL ZOO ===");
            for (int i = 0; i < llistaAnimals.getLength(); i++) {
                Node node = llistaAnimals.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    mostrarInfoAnimal(element);
                }
            }

            // 2. Bonus: Comptar quants animals hi ha
            System.out.println("==========================================");
            System.out.println("Total d'animals al zoo: " + llistaAnimals.getLength());

            // 3. Bonus: Mostrar només els animals que mengen Carn
            System.out.println("\n=== ANIMALS QUE MENGEN CARN ===");
            for (int i = 0; i < llistaAnimals.getLength(); i++) {
                Node node = llistaAnimals.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String aliment = element.getElementsByTagName("aliment").item(0).getTextContent();

                    if ("Carn".equalsIgnoreCase(aliment)) {
                        mostrarInfoAnimal(element);
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error en llegir el fitxer XML: " + e.getMessage());
        }
    }

    private static void mostrarInfoAnimal(Element element) {
        String id = element.getAttribute("id");
        String nom = element.getElementsByTagName("nom").item(0).getTextContent();
        String especie = element.getElementsByTagName("especie").item(0).getTextContent();
        String aliment = element.getElementsByTagName("aliment").item(0).getTextContent();
        String edat = element.getElementsByTagName("edat").item(0).getTextContent();

        System.out.println("Animal #" + id);
        System.out.println("Nom: " + nom);
        System.out.println("Espècie: " + especie);
        System.out.println("Aliment: " + aliment);
        System.out.println("Edat: " + edat);
        System.out.println("------------------------------------------");
    }
}