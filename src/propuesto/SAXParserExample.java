package propuesto;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserExample {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new BOEHandler();
            saxParser.parse("ej01.xml", handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class BOEHandler extends DefaultHandler {
        private boolean inTitSeccion = false;
        private boolean inOrganismo = false;
        private boolean inTexto = false;
        private boolean inPagina = false;
        private StringBuilder contenidoTexto = new StringBuilder();
        private int contadorSecciones = 0;
        private int contadorApartados = 0;

        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            switch (qName) {
                case "SECCION":
                    contadorSecciones++;
                    contadorApartados = 0;
                    break;
                case "TIT-SECCION":
                    inTitSeccion = true;
                    break;
                case "APARTADO":
                    contadorApartados++;
                    break;
                case "ORGANISMO":
                    inOrganismo = true;
                    break;
                case "TEXTO":
                    inTexto = true;
                    contenidoTexto.setLength(0);
                    break;
                case "PAGINA":
                    inPagina = true;
                    break;
            }
        }
        public void characters(char ch[], int start, int length) {
            String contenido = new String(ch, start, length).trim();
            if (!contenido.isEmpty()) {
                if (inTitSeccion) {
                    System.out.println("Título sección: " + contenido);
                    inTitSeccion = false;
                } else if (inOrganismo) {
                    System.out.println("  Organismo: " + contenido);
                    inOrganismo = false;
                } else if (inTexto) {
                    contenidoTexto.append(contenido).append(" ");
                } else if (inPagina) {
                    System.out.println("      Página: " + contenido);
                    inPagina = false;
                }
            }
        }
        public void endElement(String uri, String localName, String qName) {
            if ("TEXTO".equals(qName)) {
                System.out.println("    Texto legal: " + contenidoTexto.toString().trim());
                inTexto = false;
            } else if ("SECCION".equals(qName)) {
                System.out.println("Número de apartados: " + contadorApartados + "\n");
            }
        }
        public void startDocument() {
            System.out.println("Comenzando análisis del BOE con SAX...");
        }
        public void endDocument() {
            System.out.println("Análisis completado.");
            System.out.println("Total de secciones: " + contadorSecciones);
        }
    }
}
