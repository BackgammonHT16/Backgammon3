/**
 * 
 */
package bg.backgammon3.view.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference.Elements;

import bg.backgammon3.config.Config;
import javafx.animation.*;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Erstellt den Hintergrund
 *
 */
public class BackGroundHelper {
	private static int tmpNode = 0;
	private static int tmpNode2 = 0;
	private static int scale = 0;
	private static boolean show = true;
	private static boolean mu = false;
	private static ArrayList<Node> elemente = new ArrayList<Node>();

	
	public static void showBackground(Pane root, ImageView bgv) {
		Media media = new Media(new File(Config.getString("happySound")).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(0);
		
		tmpNode = 0;
		double bodySizeY = 20;
		double bodyPositionY = 20;
		double startX = -root.getWidth()/root.getScaleX() / 2 - 40;
		double startY = root.getHeight()/root.getScaleY() / 2 - bodySizeY - bodyPositionY;
		Group g = new Group();
		g.setTranslateX(startX);
		g.setTranslateY(startY);


		Ellipse c = new Ellipse();
		c.setTranslateX(0);
		c.setTranslateY(bodyPositionY);
		c.setRadiusX(bodySizeY);
		c.setRadiusY(bodySizeY);
		c.setFill(Color.BROWN);
		//c.setStroke(Color.BLACK);
		g.getChildren().add(c);
		


		Circle c2 = new Circle();
		c2.setTranslateX(0);
		c2.setTranslateY(-10);
		c2.setRadius(10);
		c2.setFill(Color.BROWN);
		//c2.setStroke(Color.BLACK);
		g.getChildren().add(c2);
		
		Ellipse e1 = new Ellipse();
		e1.setTranslateX(-10);
		e1.setTranslateY(-30);
		e1.setRadiusX(3);
		e1.setRadiusY(12);
		e1.setRotate(-20);
		e1.setFill(Color.BROWN);
		//e1.setStroke(Color.BLACK);
		g.getChildren().add(e1);
		

		Ellipse e2 = new Ellipse();
		e2.setTranslateX(+10);
		e2.setTranslateY(-30);
		e2.setRadiusX(3);
		e2.setRadiusY(12);
		e2.setRotate(20);
		e2.setFill(Color.BROWN);
		//e2.setStroke(Color.BLACK);
		g.getChildren().add(e2);
		

		Circle t = new Circle();
		t.setTranslateX(0);
		t.setTranslateY(bodyPositionY + bodySizeY / 2);
		t.setRadius(5);
		t.setFill(Color.WHITE);
		//t.setStroke(Color.BLACK);
		g.getChildren().add(t);

        g.setPickOnBounds(true);
		g.setMouseTransparent(false);
		
		root.getChildren().add(g);
		elemente.add(g);
		g.toFront();

		t.toFront();
		
		Path path = new Path();
		double jumpLength = 100;
		int numberJumps = 80;
		double slope = 50;
		for(int i = 0; i < numberJumps; i++) {
			path.getElements().add(new MoveTo(startX + jumpLength * i, startY));
			path.getElements().add(new CubicCurveTo(
					startX + jumpLength * i + slope, 
					startY - jumpLength, 
					startX + jumpLength * (i + 1) - slope, 
					startY - jumpLength, 
					startX + jumpLength * (i + 1), 
					startY));
		}
		PathTransition pathTransition = new PathTransition();
		
		pathTransition.setDuration(Duration.millis(40000));
		pathTransition.setNode(g);
		pathTransition.setPath(path);
		pathTransition.setCycleCount(1);
		pathTransition.setAutoReverse(false);
		c.radiusYProperty().bind(g.translateYProperty().subtract(startY - bodySizeY).multiply(0.1).add(bodySizeY));
		c.translateYProperty().bind(c.radiusYProperty().subtract(bodySizeY).add(bodyPositionY));
		t.translateYProperty().bind(c.translateYProperty().add(c.radiusYProperty().getValue() / 2));
		pathTransition.setOnFinished(a -> {
			root.getChildren().remove(g);
		});
		pathTransition.play();
		

		g.setOnMouseClicked(a -> {
			if(mu) {
				mediaPlayer.play();
			}
			for(int i = 0; i < g.getChildren().size(); i++) {
				g.getChildren().get(i).setVisible(false);
			}
			ImageView im = new ImageView();
			StaticImageHelper.loadImageView(Config.getString("happyPatch"), 70, false, 0, 0, im);
			g.getChildren().add(im);
			pathTransition.stop();
		});
	}
	
	public static void toggleVisibility() {
		show = !show;
		for(int i = 0; i < elemente.size(); i++) {
			elemente.get(i).setVisible(show);
		}
	}
	
	public static void initBackground(Pane root, ImageView bgv) {
		bgv.setOnKeyTyped(e -> {
			if(e.getCharacter().equals("s")){
				toggleVisibility();
			}
			if(tmpNode == 5) {
				if(e.getCharacter().equals("r")) {
					ArrayList<KeyFrame> kf = new ArrayList<KeyFrame>();
					scale = scale > 30 ? 30 : scale;
					if(scale == 0) {
						showBackground(root, bgv);
					}
					double time = 0;
					Random r = new Random();
					for(int i = 0; i < scale; i++) {
						time += r.nextInt(1000) + 200.0;
						kf.add(new KeyFrame(Duration.millis(time), a -> {
							showBackground(root, bgv);
						}));
					}
					Timeline timeline  = new Timeline(); 
					timeline.setCycleCount(0); 
					timeline.setAutoReverse(false); 
					timeline.getKeyFrames().addAll(kf); 
					timeline.play();
					
					scale = 0;
				} else {
					tmpNode = 0;
					scale = 0;
				}
			}
			if(tmpNode == 4) {
				if(e.getCharacter().equals("a")) {
					tmpNode = 5;
				} else {
					tmpNode = 0;
					scale = 0;
				}
			}
			if(tmpNode == 3) {
				if(e.getCharacter().equals("b")) {
					tmpNode = 4;
				} else {
					tmpNode = 0;
					scale = 0;
				}
			}
			if(tmpNode == 2) {
				if(e.getCharacter().equals("b")) {
					tmpNode = 3;
				} else {
					tmpNode = 0;
					scale = 0;
				}
			}
			if(tmpNode == 1) {
				if(e.getCharacter().equals("i")) {
					tmpNode = 2;
				} else {
					tmpNode = 0;
					scale = 0;
				}
			}
			if(e.getCharacter().equals("t")) {
				tmpNode = 1;
			}

			if(tmpNode2 == 1) {
				if(e.getCharacter().equals("u")) {
					mu = !mu;
				} else {
					tmpNode2 = 0;
				}
			}
			if(e.getCharacter().equals("m")) {
				tmpNode2 = 1;
			}
			//if(e.getCharacter().equals("m"))
			if(e.getCharacter().charAt(0) >= '0' && e.getCharacter().charAt(0) <= '9')
			{
				scale = scale * 10 + e.getCharacter().charAt(0) - '0';
			}
		});
	}
}
