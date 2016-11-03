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
import bg.backgammon3.model.action.*;
import bg.backgammon3.view.*;
import bg.backgammon3.view.javafxsvg.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * @author philipp
 *
 */
public class GameStage extends AppStage {
	private Logger logger = LogManager.getLogger(GameStage.class);
	private BoardView boardView;
	private MediaPlayer mediaPlayer;
	private Stage stage;

	public GameStage(Game game) {
		super(game);
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

		StackPane root = new StackPane();

		// BoardView erstellen
		boardView = new BoardView(game.getBoard(), root);

		Scene scene = new Scene(root, Config.getInteger("width"), Config.getInteger("height"));

		// Das Fenster soll mindestens so groß wie der Inhalt sein
		// stage.minHeightProperty().bind(Bindings.max(Config.getInteger("minHeight"),
		// stage.heightProperty().subtract(scene.heightProperty()).add(root.minHeightProperty())));

		// stage.minWidthProperty().bind(Bindings.max(Config.getInteger("minWidth"),
		// stage.widthProperty().subtract(scene.widthProperty()).add(root.minWidthProperty())));

		// stage.sizeToScene();
		// Alles anzeigen

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
		stage.setScene(scene);
		stage.show();

	}
	
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

	@Override
	public ArrayList<Node> getControls() {
		if (boardView != null) {
			return boardView.getControls();
		}
		return null;
	}

	public void update(Action action) {
		if (action instanceof UpdateSound) {
			sound(Config.getInteger("playSound") == 0 ? false : true);
		} else {
			boardView.update(action);
		}
	}

	@Override
	public void hide() {
		if (boardView != null) {
			boardView.hide();
			stage.hide();
		}
	}

	@Override
	public Stage getStage() {
		return stage;
	}

	public void sound(Boolean play) {
		if (play) {
			mediaPlayer.play();
		} else {
			mediaPlayer.stop();
		}
	}

	private void initSound(String musicFile) {
		Media media = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}
}
