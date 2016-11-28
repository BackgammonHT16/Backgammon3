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
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Erstellt den Hintergrund
 *
 */
public class BackGroundHelper {
	private static int tmpNode = 0;
	private static int tmpNode2 = 0;
	private static int tmpNode3 = 0;
	private static int scale = 0;
	private static boolean show = true;
	private static boolean mu = false;
	private static boolean mhd = false;
	private static ArrayList<Node> elemente = new ArrayList<Node>();
	private static ArrayList<MediaPlayer> mediaPlayer = new ArrayList<MediaPlayer>();
	private static int count = 0;
	private static ImageCursor image;
	private static int sizeOverall = 0;
	private static Text textOverall;

	
	public static void showBackground(Pane root, ImageView bgv, int dir, double y, double v, double s) {
		
		tmpNode = 0;
		double bodySizeY = 20;
		double bodyPositionY = 20;
		double startX = -root.getWidth()/root.getScaleX() / 2 - 40;
		if(dir == 0) {
			startX = -root.getWidth()/root.getScaleX() / 2 - 60;
		} else {
			startX = +root.getWidth()/root.getScaleX() / 2 + 60;
		}
		double startY;
		startY = root.getHeight()/root.getScaleY() / 2 - bodySizeY - bodyPositionY - y * root.getHeight()/root.getScaleY();
		
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

		g.setScaleX(s);
		g.setScaleY(s);
		
        g.setPickOnBounds(true);
		g.setMouseTransparent(false);
		
		root.getChildren().add(g);
		elemente.add(g);
		g.toFront();

		g.setVisible(show);
		t.toFront();
		
		Path path = new Path();
		double jumpLength = 100;
		int numberJumps = 80;
		double slope = 50;
		int direction = dir == 0 ? 1 : -1;
		for(int i = 0; i < numberJumps; i++) {
			path.getElements().add(new MoveTo(startX + jumpLength * i * direction, startY));
			path.getElements().add(new CubicCurveTo(
					startX + (jumpLength * i + slope) * direction, 
					startY - jumpLength, 
					startX + (jumpLength * (i + 1) - slope) * direction, 
					startY - jumpLength, 
					startX + (jumpLength * (i + 1)) * direction, 
					startY));
		}
		PathTransition pathTransition = new PathTransition();
		
		pathTransition.setDuration(Duration.millis(40000*v));
		pathTransition.setNode(g);
		pathTransition.setPath(path);
		pathTransition.setCycleCount(1);
		pathTransition.setAutoReverse(false);
		c.radiusYProperty().bind(g.translateYProperty().subtract(startY - bodySizeY).multiply(0.1).add(bodySizeY));
		c.translateYProperty().bind(c.radiusYProperty().subtract(bodySizeY).add(bodyPositionY));
		t.translateYProperty().bind(c.translateYProperty().add(c.radiusYProperty().getValue() / 2));
		pathTransition.setOnFinished(a -> {
			root.getChildren().remove(g);
			elemente.remove(g);
		});
		pathTransition.play();
		

		g.setOnMouseClicked(a -> {
			if(mu && g.getChildren().get(0).isVisible()) {
				mediaPlayer.get(count).stop();
				mediaPlayer.get(count).play();
				count = (count + 1) % mediaPlayer.size();
			}
			for(int i = 0; i < g.getChildren().size(); i++) {
				g.getChildren().get(i).setVisible(false);
			}
			ImageView im = new ImageView();
			StaticImageHelper.loadImageView(Config.getString("happyPatch"), 70, false, 0, 0, im);
			g.setMouseTransparent(true);
			g.getChildren().add(im);
			pathTransition.stop();
			
			int sizeSingle = (int)(-s * 10.0) + 20;
			sizeSingle = (sizeSingle / 3) * 10;
			sizeOverall += sizeSingle;

			textOverall.setText(Integer.toString(sizeOverall));
			textOverall.toFront();
			
			Text text = new Text();
			text.setStrokeWidth(1);
			text.setStroke(Color.WHITE);
			text.setText(Integer.toString(sizeSingle));
			text.setTranslateX(g.translateXProperty().getValue());
			text.setTranslateY(g.translateYProperty().getValue());
			text.setFont(Font.font(Config.getString("Font"), FontWeight.BOLD, Config.getInteger("textSize")*3));
			elemente.add(text);
			root.getChildren().add(text);

			ArrayList<KeyFrame> kf = new ArrayList<KeyFrame>();
			KeyFrame k = new KeyFrame(Duration.millis(0), new KeyValue(text.scaleXProperty(), 1));
			kf.add(k);
			k = new KeyFrame(Duration.millis(0), new KeyValue(text.scaleYProperty(), 1));
			kf.add(k);
			k = new KeyFrame(Duration.millis(0), new KeyValue(text.opacityProperty(), 1));
			kf.add(k);

			k = new KeyFrame(Duration.millis(1000), new KeyValue(text.scaleXProperty(), 6));
			kf.add(k);
			k = new KeyFrame(Duration.millis(1000), new KeyValue(text.scaleYProperty(), 6));
			kf.add(k);
			k = new KeyFrame(Duration.millis(1000), new KeyValue(text.opacityProperty(), 0));
			kf.add(k);
			
			k = new KeyFrame(Duration.millis(1000), b -> {root.getChildren().remove(text); elemente.remove(text);});
			kf.add(k);

			Timeline timeline  = new Timeline(); 
			timeline.setCycleCount(0); 
			timeline.setAutoReverse(false); 
			timeline.getKeyFrames().addAll(kf); 
			timeline.play();
		});
	}
	

	public static void showBackground(Pane root, ImageView bgv) {
		showBackground(root, bgv, 0, 0, 1, 1);
	}
	
	public static void activateInstantAction(Pane root, ImageView bgv) {
		root.setCursor(image);
		ArrayList<KeyFrame> kf = new ArrayList<KeyFrame>();
		
		

		textOverall.setVisible(true);
		textOverall.setText(Integer.toString(sizeOverall));
		textOverall.setFont(Font.font(Config.getString("Font"), FontWeight.BOLD, Config.getInteger("textSize") * 3));
		textOverall.setTranslateX(-Config.getInteger("width")/2 + 80);
		textOverall.setTranslateY(-Config.getInteger("height")/2 + 40);
		textOverall.setStroke(Color.WHITE);
		textOverall.setStrokeWidth(1);
		
		
		scale = scale > 180 ? 180 : scale;
		scale = scale < 30 ? 30 : scale;
		double time = 0;
		Random r = new Random();
		while(time < scale * 1000) {
			time += r.nextInt(1000) + 200.0;
			kf.add(new KeyFrame(Duration.millis(time), a -> {
				int dir = r.nextInt(2);
				double y = r.nextDouble() * 0.75;
				double v = r.nextDouble() * 0.6 + 0.5;
				double s = r.nextDouble() + 0.5;

				showBackground(root, bgv, dir, y, v, s);
			}));
		}
		kf.add(new KeyFrame(Duration.millis(time + 8000), a -> {
			Text text = new Text();
			text.setText(Integer.toString(sizeOverall));
			text.setStrokeWidth(1);
			text.setStroke(Color.WHITE);
			text.setTranslateX(0);
			text.setTranslateY(0);
			text.setFont(Font.font(Config.getString("Font"), FontWeight.BOLD, Config.getInteger("textSize") * 6));
			elemente.add(text);
			root.getChildren().add(text);

			ArrayList<KeyFrame> kf2 = new ArrayList<KeyFrame>();
			KeyFrame k = new KeyFrame(Duration.millis(0), new KeyValue(text.scaleXProperty(), 1));
			kf2.add(k);
			k = new KeyFrame(Duration.millis(0), new KeyValue(text.scaleYProperty(), 1));
			kf2.add(k);
			k = new KeyFrame(Duration.millis(0), new KeyValue(text.opacityProperty(), 1));
			kf2.add(k);

			k = new KeyFrame(Duration.millis(4000), new KeyValue(text.scaleXProperty(), 10));
			kf2.add(k);
			k = new KeyFrame(Duration.millis(4000), new KeyValue(text.scaleYProperty(), 10));
			kf2.add(k);
			k = new KeyFrame(Duration.millis(4000), new KeyValue(text.opacityProperty(), 0));
			kf2.add(k);
			
			k = new KeyFrame(Duration.millis(4000), b -> {root.getChildren().remove(text); elemente.remove(text);});
			kf2.add(k);

			Timeline timeline  = new Timeline(); 
			timeline.setCycleCount(0); 
			timeline.setAutoReverse(false); 
			timeline.getKeyFrames().addAll(kf2); 
			timeline.play();
			sizeOverall = 0; 
		}));
		kf.add(new KeyFrame(Duration.millis(time + 9000), a -> {mhd = false; root.setCursor(Cursor.DEFAULT); }));
		Timeline timeline  = new Timeline(); 
		timeline.setCycleCount(0); 
		timeline.setAutoReverse(false); 
		timeline.getKeyFrames().addAll(kf); 
		timeline.play();
		
		scale = 0;
	}
	
	public static void toggleVisibility(Pane root) {
		show = !show;
		for(int i = 0; i < elemente.size(); i++) {
			elemente.get(i).setVisible(show);
		}
		if(show) {
			root.setCursor(image);
		} else {
			root.setCursor(Cursor.DEFAULT);
		}
	}
	
	public static void initBackground(Pane root, ImageView bgv) {
		Media media = new Media(new File(Config.getString("happySound")).toURI().toString());
		for(int i = 0; i < 10; i++) {
			mediaPlayer.add(new MediaPlayer(media));
			mediaPlayer.get(i).setCycleCount(0);
		}
		image = new ImageCursor(StaticImageHelper.loadImage(Config.getString("happyVision")));

		textOverall = new Text();
		textOverall.setVisible(false);
		elemente.add(textOverall);
		root.getChildren().add(textOverall);
		
		bgv.setOnKeyTyped(e -> {
			if(e.getCharacter().equals("s")){
				toggleVisibility(root);
			}
			if(tmpNode == 5) {
				if(e.getCharacter().equals("r")) {
					root.setCursor(image);
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
					kf.add(new KeyFrame(Duration.millis(time + 3000), a -> {root.setCursor(Cursor.DEFAULT); }));
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
			

			if(tmpNode3 == 1) {
				if(e.getCharacter().equals("c")) {
					if(!mhd) {
						mhd = true;
						activateInstantAction(root, bgv);
					}
				} else {
					tmpNode3 = 0;
				}
			}
			if(e.getCharacter().equals("q")) {
				tmpNode3 = 1;
			}
			
			//if(e.getCharacter().equals("m"))
			if(e.getCharacter().charAt(0) >= '0' && e.getCharacter().charAt(0) <= '9')
			{
				scale = scale * 10 + e.getCharacter().charAt(0) - '0';
			}
		});
	}
}
