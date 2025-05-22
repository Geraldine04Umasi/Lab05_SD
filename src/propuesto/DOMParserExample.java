package propuesto;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
/**
 * Análisis de ej01.xml usando DOM
 * @author Geraldine
 */
public class DOMParserExample {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("ej01.xml");
            doc.getDocumentElement().normalize();

            System.out.println("Iniciando análisis con DOM...");
            Element raiz = doc.getDocumentElement();
            System.out.println("Elemento raíz: " + raiz.getNodeName());

            NodeList secciones = doc.getElementsByTagName("SECCION");
            System.out.println("Número de secciones: " + secciones.getLength());

            for (int i = 0; i < secciones.getLength(); i++) {
                Element seccion = (Element) secciones.item(i);
                String titulo = seccion.getElementsByTagName("TIT-SECCION").item(0).getTextContent();
                System.out.println("Título sección: " + titulo);
                NodeList apartados = seccion.getElementsByTagName("APARTADO");
                for (int j = 0; j < apartados.getLength(); j++) {
                    Element apartado = (Element) apartados.item(j);
                    String organismo = apartado.getElementsByTagName("ORGANISMO").item(0).getTextContent();
                    System.out.println("  Organismo: " + organismo);
                    NodeList parrafos = apartado.getElementsByTagName("PARRAFO");
                    for (int k = 0; k < parrafos.getLength(); k++) {
                        Element parrafo = (Element) parrafos.item(k);
                        String textoLegal = parrafo.getElementsByTagName("TEXTO").item(0).getTextContent().trim();
                        System.out.println("    Texto legal: " + textoLegal);
                        NodeList paginas = parrafo.getElementsByTagName("PAGINA");
                        for (int p = 0; p < paginas.getLength(); p++) {
                            String pagina = paginas.item(p).getTextContent().trim();
                            System.out.println("      Página: " + pagina);
                        }
                    }
                }
                System.out.println("Número de apartados: " + apartados.getLength() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
