/**
 * 
 */
package bg.backgammon3.config;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

//import java.io.File;
import java.io.IOException;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Diese Klasse stellt dem Programm Daten aus einem Konfigurationsfile zur
 * Verfügung.
 * 
 * @author philipp
 *
 */
public class Config {
	private static Logger logger = LogManager.getLogger(Config.class);

	// Enthält die Daten im Format: "Schlüssel" -> Wert
	private static LinkedHashMap<String, Object> config;

	// Enthält den Dateinamen der Konfigurationsdatei
	private static String fileName;

	public static void initConfig() {
		initConfig("res/config.xml");
	}

	private static XMLStreamWriter xtw = null;

	/**
	 * Gibt den zu dem gegebenen Schlüssel zugehörigen Integer Wert zurück.
	 * 
	 * @param property
	 *            Schlüssel des Wertes.
	 * @return Zu property zugehöriger Integer Wert.
	 */
	public static Integer getInteger(String property) {
		return (Integer) config.get(property);
	}

	/**
	 * Ändert den Wert zu der übergebenen property. Diese Änderung wird in der
	 * XML Datei abgespeichert. TODO Geänderten Wert abspeichern.
	 * 
	 * @param property
	 *            Schlüssel des zu ändernden Wertes.
	 * @param value
	 *            Wert der aktualisiert werden soll.
	 */
	public static void setInteger(String property, int value) {
		if (getInteger("doNotUpdate") == 1) {
			return;
		}
		writeConfig(fileName, property, value);
		if (config.replace(property, value) == null) {
			logger.warn("Nicht vorhandener Wert überschrieben.");
		}
	}

	/**
	 * Gibt den zu dem gegebenen Schlüssel zugehörigen String zurück.
	 * 
	 * @param property
	 *            Schlüssel des Wertes.
	 * @return Zu property zugehöriger String Wert.
	 */
	public static String getString(String property) {
		return (String) config.get(property);
	}

	/**
	 * Ändert den Wert zu der übergebenen property. Diese Änderung wird in der
	 * XML Datei abgespeichert. TODO Geänderten Wert abspeichern.
	 * 
	 * @param property
	 *            Schlüssel des zu ändernden Wertes.
	 * @param value
	 *            Wert der aktualisiert werden soll.
	 */
	public static void setString(String property, String value) {
		if (getInteger("doNotUpdate") == 1) {
			return;
		}
		// writeConfig(fileName,property,value);
		if (config.replace(property, value) == null) {
			logger.warn("Nicht vorhandener Wert überschrieben.");
		}
	}

	/**
	 * Lädt die Konfigurationsdaten aus einer XML Datei in die Variable config
	 * 
	 * @param XMLFilename
	 *            Name der XML Datei die die Konfigurationsdaten enthält
	 */
	public static void initConfig(String XMLFilename) {
		fileName = XMLFilename;
		try {

			File file = new File(XMLFilename);

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = dBuilder.parse(file);

			if (doc.hasChildNodes()) {

				NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
				config = new LinkedHashMap<String, Object>();

				// Durch alle Kind Elemente iterieren und diese in config
				// sammeln.
				for (int count = 0; count < nodeList.getLength(); count++) {

					Node tempNode = nodeList.item(count);

					if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

						logger.info(tempNode.getNodeName() + " " + tempNode.getTextContent());

						// unterscheiden zwischen Integer und String
						if (((String) tempNode.getTextContent()).matches("^-?\\d+$"))
							config.put(tempNode.getNodeName(), Integer.parseInt(tempNode.getTextContent()));
						else {
							config.put(tempNode.getNodeName(), tempNode.getTextContent());
						}
					}
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	public static void writeConfig(String XMLFilename, String property, int value) {
		logger.info("Filename: " + fileName + " Property: " + property + " Value: " + value);
		// fileName = XMLFilename;
		boolean changed = false;
		try {

			File file = new File(XMLFilename);

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = dBuilder.parse(file);

			if (doc.hasChildNodes()) {

				NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
				// config = new LinkedHashMap<String, Object>();

				// Durch alle Kind Elemente iterieren und temp vergleichen mit
				// property, TODO null abfangen
				for (int count = 0; (count < nodeList.getLength() && changed == false); count++) {

					Node tempNode = nodeList.item(count);

					if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

						// logger.info(tempNode.getNodeName() + " " +
						// tempNode.getTextContent());

						// unterscheiden zwischen Integer und String
						logger.info("NodeName: " + tempNode.getNodeName() + " Property: " + property);
						if ((property).equals(tempNode.getNodeName())) {
							logger.info("Vergleich zwischen " + tempNode.getNodeName() + " und " + property
									+ " ist gleich.");
							changed = true;
							tempNode.setNodeValue(Integer.toString(value));

						}
					}
				}

			}

//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
		
		// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File(fileName));
					transformer.transform(source, result);

					//System.out.println("Done");
					logger.info("in config geschrieben!");

				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				} catch (TransformerException tfe) {
					tfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (SAXException sae) {
					sae.printStackTrace();
				}
	}

	/*public static void test(String argv[]) {

		try {
			String filepath = fileName;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the root element
			Node company = doc.getFirstChild();

			// Get the staff element , it may not working if tag has spaces, or
			// whatever weird characters in front...it's better to use
			// getElementsByTagName() to get it directly.
			// Node staff = company.getFirstChild();

			// Get the staff element by tag name directly
			Node staff = doc.getElementsByTagName("staff").item(0);

			// update staff attribute
			NamedNodeMap attr = staff.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("2");

			// append a new node to staff
			Element age = doc.createElement("age");
			age.appendChild(doc.createTextNode("28"));
			staff.appendChild(age);

			// loop the staff child node
			NodeList list = staff.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {

				Node node = list.item(i);

				// get the salary element, and update the value
				if ("salary".equals(node.getNodeName())) {
					node.setTextContent("2000000");
				}

				// remove firstname
				if ("firstname".equals(node.getNodeName())) {
					staff.removeChild(node);
				}

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}
*/
}
