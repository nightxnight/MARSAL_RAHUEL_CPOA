package utils.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLBuilder {

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	
	private Element rootElement;
	private ArrayList<Element> attributes;
	
	public XMLBuilder() {
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch(Exception e) {
			System.out.println("Erreur lors de la creation du xml");
		}
		attributes = new ArrayList<Element>();
	}
	
	public void setRoot(String rootName) {
		rootElement = document.createElement("config");
	}
	
	public void createElement(String elementName, String value) {
		Element element = document.createElement(elementName);
		element.appendChild(document.createTextNode(value));
		attributes.add(element);
	}
	
	public void createXMLFile(String nomFichier) {
		document.appendChild(rootElement);
		for (int i = 0; i < attributes.size(); i++) {
			rootElement.appendChild(attributes.get(i));
		}
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			DOMSource source = new DOMSource(document);
			StreamResult file = new StreamResult(new File("resources/" + nomFichier + ".xml"));
			
			transformer.transform(source, file);
		} catch (Exception e) {
			System.out.println("Erreur lors de la creation du fichier xml");
		}
	}
	
}
