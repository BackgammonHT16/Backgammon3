/**
 * 
 */
package bg.backgammon3.view.stage;

import java.io.File;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.view.*;
import bg.backgammon3.view.javafxsvg.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;

/**
 * Die GameStage
 *
 */
public class GameStage {
	private Logger logger = LogManager.getLogger(GameStage.class);
	private BoardView boardView;
	private MediaPlayer mediaPlayer;
	private Stage stage;
	private Game game;
	private Board board;
	private StackPane root;

	/**
	 * Konstruktor
	 * @param game Das Game Model
	 */
	public GameStage(Game game) {
		this.game = game;
		initGameStage();
	}

	/**
	 * Initialisiert die GameStage. Hier wird Das Board und die alle Graphik
	 * sowie sound Komponenten erstellt.
	 */
	public void initGameStage() {

		// SVG Unterstützung
		SvgImageLoaderFactory.install();

		// Stage und scene erstellen
		stage = new Stage();

		root = new StackPane();

		// BoardView erstellen
		boardView = new BoardView(game.getBoard(), root);

		Scene scene = new Scene(root, Config.getInteger("width"), Config.getInteger("height"));
		initSound(Config.getString("soundFile"));
		if (Config.getInteger("playSound") == 1) {
			sound(true);
		}

		scene.widthProperty().addListener(e -> {
			handleSizeChange(scene, root);
		});
		scene.heightProperty().addListener(e -> {
			handleSizeChange(scene, root);
		});

		stage.setMinWidth(Config.getInteger("minWidth") + 50);
		stage.setMinHeight(Config.getInteger("minHeight") + 70);
		
		handleSizeChange(scene, root);

		// Alles anzeigen
		stage.setScene(scene);
		//stage.show();
		logger.info("Spiel wird angezeigt.");
	}
	
	/**
	 * Ändert die skalierung
	 * @param scene die scene
	 * @param root die root
	 */
	private void handleSizeChange(Scene scene, StackPane root) {
		double zoomx = 1;
		double zoomy = 1;
		if(scene.getWidth() > Config.getInteger("minWidth")) {
			zoomx = scene.getWidth() / (double) Config.getInteger("width");
		} else {
			zoomx = (double) Config.getInteger("minWidth") / (double) Config.getInteger("width");
		}
		if(scene.getHeight() > Config.getInteger("minHeight")) {
			zoomy = scene.getHeight() / (double) Config.getInteger("height");
		} else {
			zoomy = (double) Config.getInteger("minHeight") / (double) Config.getInteger("height");
		}
		root.setScaleX(Math.min(zoomx, zoomy));
		root.setScaleY(Math.min(zoomx, zoomy));
	}


	/**
	 * gibt die controls zurück
	 * @return die Controls
	 */
	public ArrayList<Node> getControls() {
		if (boardView != null) {
			return boardView.getControls();
		}
		return null;
	}
/*
	public int update(Action action) {
		if (action instanceof UpdateSound) {
			sound(Config.getInteger("playSound") == 0 ? false : true);
		} else {
			return boardView.update(action);
		}
		return 0;
	}
*/
	//@Override
	/**
	 * schließt diese ansicht
	 */
	public void hide() {
		if (boardView != null) {
			boardView.hide();
			stage.hide();
			sound(false);
		}
	}

	//@Override
	/**
	 * Gibt die stage zurück
	 * @return die Stage
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * toggelt den sound
	 */
	public void sound() {
		sound(Config.getInteger("playSound") == 0 ? false : true);
	}

	/**
	 * Schaltet den sound an oder aus
	 * @param play sound spielt bei wahr
	 */
	private void sound(Boolean play) {
		if (play) {
			if(!mediaPlayer.getStatus().equals(Status.PLAYING)) {
				mediaPlayer.play();
			}
		} else {
			mediaPlayer.stop();
		}
	}

	/**
	 * Initialisiert den den sound
	 * @param musicFile der Soundfile
	 */
	private void initSound(String musicFile) {
		Media media = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}

	/**
	 * Aktualisiert diese Stage
	 * @return Wahr wenn Boardview aktualisiert werden muss
	 */
	public boolean update() {
		boolean updateControls = false;
		if(game.getBoard() != board) {
			boardView = new BoardView(game.getBoard(), root);
			updateControls = true;
			board = game.getBoard();
		}
		
		if(game.getGameRunningState().isActiv()){
			stage.show();
			boardView.update();
			sound();
		} else {
			hide();
		}
		return updateControls;
	}
}
