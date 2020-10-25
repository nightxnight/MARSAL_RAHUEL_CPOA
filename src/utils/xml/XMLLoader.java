package utils.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLLoader {

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	
	public XMLLoader(String fileName) {
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse("resources/" + fileName + ".xml");
		} catch (Exception e) {
			System.out.println("erreur chargement fichier xml");
		}
	}
	
	public Document getDocument() throws NullPointerException {
		if(document==null) throw new NullPointerException();
		return document;
	}
	
	public void updateElement(String elementName, String value) {
		Element element = (Element) document.getElementsByTagName(elementName).item(0);
		element.setTextContent(value);
	}
	
	public void saveChanges(Document doc, String fileName) {
		try {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("resources/" + fileName + ".xml"));
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        
		} catch (Exception e) {
			System.out.println("Echec lors de la sauvegarde des modification de " + fileName + ".xml");
		}
	}
}