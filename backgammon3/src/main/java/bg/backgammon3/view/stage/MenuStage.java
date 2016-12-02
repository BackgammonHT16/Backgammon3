/**
 * 
 */
package bg.backgammon3.view.stage;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import bg.backgammon3.model.Game;
import bg.backgammon3.model.Menu;
import bg.backgammon3.view.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Die MenuStage
 *
 */
public class MenuStage  {
	private Logger logger = LogManager.getLogger(MenuStage.class);

	private ArrayList<Node> controls = new ArrayList<Node>();

	// Speichert die appStage des Spiels ab solange das Menu angezeigt wird.
	//private AppStage appStage;
	private GameStage gameStage;

	// Das Menu
	private Stage stage;

	// Model Klasse
	private Menu menu = new Menu();
	
	private Game game;

	// Continue Button wird ausgelagert um ihn deaktivieren zu können falls das
	// Spiel beendet wird während
	// das Menu angezeigt wird.
	private Button buttonContinue;

	/**
	 * Konstruktor
	 * @param game das Game model
	 */
	public MenuStage(Game game) {
		this.game = game;
		menu = game.getMenu();
		//appStage = null;
		gameStage = null;
		initMenu();
	}

	/**
	 * Falls das Spiel bereits geöffnet ist wird es in appStage zwischen
	 * gespeichert. So kann es am ende wieder von GameController übernommen
	 * werden.
	 * 
	 * @param game
	 *            Handle zur Hauptklasse des Modells
	 * @param appStage
	 *            Handle zur GameStage
	 */
	public MenuStage(Game game, GameStage appStage) {
		this.game = game;
		//this.appStage = appStage;
		this.gameStage = appStage;
		initMenu();
	}

	/**
	 * Gibt die Gamestage zurück
	 * @return
	 */
	public GameStage getGameStage() {
		return gameStage;
	}

	/**
	 * Zeigt das Menu an
	 */
	private void initMenu() {
		StackPane root = new StackPane();

		stage = new Stage();

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		Label color = new Label("Color");
		grid.add(color, 0, 0);

		ObservableList<String> optionsColor = FXCollections.observableArrayList("Blue", "Red");
		final ComboBox<String> comboBoxColor = new ComboBox<String>(optionsColor);
		comboBoxColor.setValue(optionsColor.get(menu.getColor()));
		grid.add(comboBoxColor, 1, 0);

		Label difficulty = new Label("Difficulty");
		grid.add(difficulty, 0, 1);

		ObservableList<String> optionsDifficulty = FXCollections.observableArrayList("Easy", "Hard");
		final ComboBox<String> comboBoxDifficulty = new ComboBox<String>(optionsDifficulty);
		comboBoxDifficulty.setValue(optionsDifficulty.get(menu.getDifficulty() - 1));
		grid.add(comboBoxDifficulty, 1, 1);

		Label sound = new Label("Sound");
		grid.add(sound, 0, 2);

		// A checkbox with a string caption
		final CheckBox checkBoxSound = new CheckBox();
		checkBoxSound.setSelected(menu.getSound());
		grid.add(checkBoxSound, 1, 2);

		Label time = new Label("Time");
		grid.add(time, 0, 3);

		ObservableList<String> optionsTime = FXCollections.observableArrayList("Endless", "30s", "40s", "50s", "60s");
		final ComboBox<String> comboBoxTime = new ComboBox<String>(optionsTime);
		int timeId = 0;
		if (menu.getTime() == -1) {
			timeId = 0;
		} else if (menu.getTime() == 30) {
			timeId = 1;
		} else if (menu.getTime() == 40) {
			timeId = 2;
		} else if (menu.getTime() == 50) {
			timeId = 3;
		} else if (menu.getTime() == 60) {
			timeId = 4;
		}
		comboBoxTime.setValue(optionsTime.get(timeId));
		grid.add(comboBoxTime, 1, 3);

		AnchorPane anchorpane = new AnchorPane();
		final Button buttonStartGame = new StartButtonView("Start Game", menu);
		buttonContinue = new ContinueButtonView("Continue Game", menu);
		Button buttonQuit = new QuitButtonView("Quit", menu);

		comboBoxColor.setOnAction((event) -> {
			menu.setColor(((String) comboBoxColor.getValue()).equals("Blue") ? 0 : 1);
		});

		comboBoxDifficulty.setOnAction((event) -> {
			menu.setDifficulty(((String) comboBoxDifficulty.getValue()).equals("Easy") ? 1 : 2);
		});

		checkBoxSound.setOnAction((event) -> {
			menu.setSound(checkBoxSound.isSelected());
		});

		comboBoxTime.setOnAction((event) -> {
			if (((String) comboBoxTime.getValue()).equals("Endless")) {
				menu.setTime(-1);
			} else if (((String) comboBoxTime.getValue()).equals("30s")) {
				menu.setTime(30);
			} else if (((String) comboBoxTime.getValue()).equals("40s")) {
				menu.setTime(40);
			} else if (((String) comboBoxTime.getValue()).equals("50s")) {
				menu.setTime(50);
			} else if (((String) comboBoxTime.getValue()).equals("60s")) {
				menu.setTime(60);
			}
		});

		HBox hb = new HBox();
		hb.setPadding(new Insets(0, 10, 10, 10));
		hb.setSpacing(10);
		hb.getChildren().add(buttonStartGame);
		controls.add(buttonStartGame);
		if (game.gameCanContinue())
		// if(appStage != null)
		{
			// buttonContinue.setDefaultButton(true);
			hb.getChildren().add(buttonContinue);
			controls.add(buttonContinue);
		} else {
			// buttonStartGame.setDefaultButton(true);
		}
		hb.getChildren().add(buttonQuit);
		controls.add(buttonQuit);

		anchorpane.getChildren().addAll(grid, hb);
		AnchorPane.setBottomAnchor(hb, 8.0);
		AnchorPane.setRightAnchor(hb, 5.0);
		AnchorPane.setTopAnchor(grid, 10.0);

		root.getChildren().add(anchorpane);

		Scene scene = new Scene(root, 250, 300);

		// mindestgröße ist der Inhalt
		stage.minHeightProperty().bind(Bindings.max(250,
				stage.heightProperty().subtract(scene.heightProperty()).add(root.minHeightProperty())));

		stage.minWidthProperty().bind(
				Bindings.max(300, stage.widthProperty().subtract(scene.widthProperty()).add(root.minWidthProperty())));

		if (game.gameCanContinue()) {
			buttonContinue.requestFocus();
			// buttonContinue.setOnAction(e->{buttonContinue.fire();});
		} else {
			buttonStartGame.requestFocus();
		}

		stage.setTitle("Menu");
		stage.setScene(scene);
		//stage.show();

	}

	//@Override
	/**
	 * gibt die controls zurück
	 * @return die Controls
	 */
	public ArrayList<Node> getControls() {
		return controls;
	}

	
	//@Override
	/**
	 * Deaktiviert den Continue Button
	 */
	public void disableContinueButton() {
		if (buttonContinue != null) {
			logger.info("Continue Button wird disabled.");
			buttonContinue.setDisable(true);
		}
	}

	//@Override
	/**
	 * Schließt diese stage
	 */
	public void hide() {
		if (stage != null) {
			stage.hide();
		}
	}

	//@Override
	/**
	 * gibt die Stage zurück
	 * @return die stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * gibt das Menuelement zurück
	 * @return das Menuelement
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Aktualisiert diese Stage
	 * @return false
	 */
	public boolean update() {
		if(game.getMenuState().isActiv()){
			stage.show();
		} else {
			hide();
		}
		return false;
	}
}
