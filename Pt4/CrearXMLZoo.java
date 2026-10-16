package Pt4;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CrearXMLZoo {

    private static final String FITXER_XML = "Pt4/zoo.xml";

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Element arrel <zoo>
            Element rootElement = doc.createElement("zoo");
            doc.appendChild(rootElement);

            // Afegir animals d'exemple
            afegirAnimal(doc, rootElement, "1", "Pingüí", "Aptenodytes forsteri", "Peix", "5");
            afegirAnimal(doc, rootElement, "2", "Lleó", "Panthera leo", "Carn", "8");
            afegirAnimal(doc, rootElement, "3", "Tigre", "Panthera tigris", "Carn", "4");
            afegirAnimal(doc, rootElement, "4", "Elefant", "Loxodonta africana", "Herba", "12");

            // Escriure el contingut en el fitxer XML amb indentació
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FITXER_XML));

            transformer.transform(source, result);
            System.out.println("Fitxer XML creat amb èxit a: " + FITXER_XML);

        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Error en crear el fitxer XML: " + e.getMessage());
        }
    }

    private static void afegirAnimal(Document doc, Element root, String id, String nom, String especie, String aliment, String edat) {
        Element animal = doc.createElement("animal");
        animal.setAttribute("id", id);

        Element elemNom = doc.createElement("nom");
        elemNom.appendChild(doc.createTextNode(nom));
        animal.appendChild(elemNom);

        Element elemEspecie = doc.createElement("especie");
        elemEspecie.appendChild(doc.createTextNode(especie));
        animal.appendChild(elemEspecie);

        Element elemAliment = doc.createElement("aliment");
        elemAliment.appendChild(doc.createTextNode(aliment));
        animal.appendChild(elemAliment);

        Element elemEdat = doc.createElement("edat");
        elemEdat.appendChild(doc.createTextNode(edat));
        animal.appendChild(elemEdat);

        root.appendChild(animal);
    }
}