/**
 * 
 */
package bg.backgammon3.view.stage;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.model.action.*;
import bg.backgammon3.view.*;
import bg.backgammon3.view.javafxsvg.*;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * @author philipp
 *
 */
public class GameStage extends AppStage {
	private Logger logger = LogManager.getLogger(GameStage.class);
	private BoardView boardView;
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

		// Zoom
		root.setScaleX(0.5);
		root.setScaleY(0.5);
		
		// BoardView erstellen
		boardView = new BoardView(game.getBoard(), root);

		Scene scene = new Scene(root, Config.getInteger("width") / 2, Config.getInteger("height") / 2);

		// Das Fenster soll mindestens so groß wie der Inhalt sein
		// stage.minHeightProperty().bind(Bindings.max(Config.getInteger("minHeight"),
		// 		stage.heightProperty().subtract(scene.heightProperty()).add(root.minHeightProperty())));

		// stage.minWidthProperty().bind(Bindings.max(Config.getInteger("minWidth"),
		// 		stage.widthProperty().subtract(scene.widthProperty()).add(root.minWidthProperty())));

		// stage.sizeToScene();
		// stage.setMinWidth(stage.getWidth());
		// stage.setMinHeight(stage.getHeight());
		// Alles anzeigen
		stage.setScene(scene);
		stage.show();

	}

	@Override
	public ArrayList<Node> getControls() {
		if (boardView != null) {
			return boardView.getControls();
		}
		return null;
	}

	public void update(Action action) {
		boardView.update(action);
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
}
