/**
 * 
 */
package bg.backgammon3.config;

import java.io.File;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Diese Klasse stellt dem Programm Daten aus einem Konfigurationsfile zur Verfügung.
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

	/**
	 * Gibt den zu dem gegebenen Schlüssel zugehörigen Integer Wert zurück.
	 * @param property Schlüssel des Wertes.
	 * @return Zu property zugehöriger Integer Wert.
	 */
	public static Integer getInteger(String property)
	{
		return (Integer) config.get(property);
	}
	
	/**
	 * Ändert den Wert zu der übergebenen property. Diese Änderung wird in der XML Datei abgespeichert.
	 * TODO Geänderten Wert abspeichern.
	 * @param property Schlüssel des zu ändernden Wertes.
	 * @param value Wert der aktualisiert werden soll.
	 */
	public static void setInteger(String property, int value)
	{
		if(config.replace(property, value) == null)
		{
			logger.warn("Nicht vorhandener Wert überschrieben.");
		}
	}

	/**
	 * Gibt den zu dem gegebenen Schlüssel zugehörigen String zurück.
	 * @param property Schlüssel des Wertes.
	 * @return Zu property zugehöriger String Wert.
	 */
	public static String getString(String property)
	{
		return (String) config.get(property);
	}
	
	/**
	 * Ändert den Wert zu der übergebenen property. Diese Änderung wird in der XML Datei abgespeichert.
	 * TODO Geänderten Wert abspeichern.
	 * @param property Schlüssel des zu ändernden Wertes.
	 * @param value Wert der aktualisiert werden soll.
	 */
	public static void setString(String property, int value)
	{
		if(config.replace(property, value) == null)
		{
			logger.warn("Nicht vorhandener Wert überschrieben.");
		}
	}
	
	/**
	 * Lädt die Konfigurationsdaten aus einer XML Datei in die Variable config
	 * @param XMLFilename Name der XML Datei die die Konfigurationsdaten enthält
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
				
				// Durch alle Kind Elemente iterieren und diese in config sammeln. 
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
}
