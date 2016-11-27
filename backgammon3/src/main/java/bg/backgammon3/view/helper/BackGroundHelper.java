/**
 * 
 */
package bg.backgammon3.view.helper;

import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
	
	public static void initBackground(Pane root, ImageView bgv) {
		bgv.setOnKeyTyped(e -> {
			if(tmpNode == 5) {
				if(e.getCharacter().equals("r")) {
					tmpNode = 0;
					double bodySizeY = 20;
					double bodyPositionY = 20;
					double startX = -root.getWidth() / 2 - 40;
					double startY = root.getHeight() / 2 - bodySizeY - bodyPositionY;
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
					
					root.getChildren().add(g);
					g.toFront();
					t.toFront();
					
					Path path = new Path();
					double jumpLength = 100;
					int numberJumps = 40;
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
					pathTransition.setDuration(Duration.millis(20000));
					pathTransition.setNode(g);
					pathTransition.setPath(path);
					pathTransition.setCycleCount(1);
					pathTransition.setAutoReverse(false);
					c.radiusYProperty().bind(g.translateYProperty().subtract(startY - bodySizeY).multiply(0.1).add(bodySizeY));
					c.translateYProperty().bind(c.radiusYProperty().subtract(bodySizeY).add(bodyPositionY));
					t.translateYProperty().bind(c.translateYProperty().add(c.radiusYProperty().getValue() / 2));
					pathTransition.play();
				} else {
					tmpNode = 0;
				}
			}
			if(tmpNode == 4) {
				if(e.getCharacter().equals("a")) {
					tmpNode = 5;
				} else {
					tmpNode = 0;
				}
			}
			if(tmpNode == 3) {
				if(e.getCharacter().equals("b")) {
					tmpNode = 4;
				} else {
					tmpNode = 0;
				}
			}
			if(tmpNode == 2) {
				if(e.getCharacter().equals("b")) {
					tmpNode = 3;
				} else {
					tmpNode = 0;
				}
			}
			if(tmpNode == 1) {
				if(e.getCharacter().equals("i")) {
					tmpNode = 2;
				} else {
					tmpNode = 0;
				}
			}
			if(e.getCharacter().equals("t")) {
				tmpNode = 1;
			}
		});
	}
}
