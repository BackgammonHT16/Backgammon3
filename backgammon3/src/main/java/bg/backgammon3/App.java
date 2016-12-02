package bg.backgammon3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Anfangs Klasse des Spiels. 
 *
 */
public class App extends Application
{
	private Logger logger = LogManager.getLogger(App.class);

	/**
	 * Wird beim Programmstart aufgerufen
	 * @param stage Wurzel Knoten für die gesamte Darstellung des Spiels
	 */
	@Override
	public void start(Stage stage)
	{
		initGameStage();
		logger.info("Spiel gestartet");
	}
	
	/**
	 * Initialisiert die Game Stage
	 */
	private void initGameStage() {
		// Konfigurationsdaten laden
		Config.initConfig();
		
		// Damit wird der Controller Geladen
		new GameController();
	}

	/**
	 * Main Funktion
	 * @param args Wird nicht verwendet
	 */
    public static void main( String[] args )
    {
    	launch(args);
    }
}
