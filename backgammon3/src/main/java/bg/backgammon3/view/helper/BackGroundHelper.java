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
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Erstellt den Hintergrund
 *
 */
public class BackGroundHelper {
	private static int tmpNode = 0;
	private static int tmpNode2 = 0;
	private static int tmpNode3 = 0;
	private static int tmpNode4 = 0;
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
	
	private static boolean li = false;


	private static boolean moveLeft = false;
	private static boolean moveRight = false;
	private static double speedBallX;
	private static double speedBallY;
	private static Circle ball;
	private static int score;
	
	/**
	 * Zeigt den Hintergrund an
	 * @param root Root Objekt
	 * @param bgv der Hintergrund
	 * @param dir Die Anzahl
	 * @param y Die Y Koordinate
	 * @param v Die Ausrichtung
	 * @param s Die Skalierung
	 */
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
			text.mouseTransparentProperty().set(true);
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
	
	/**
	 * zeigt den Hintergrund an
	 * @param root das Root element
	 * @param bgv der hintergrund
	 */
	public static void showBackground(Pane root, ImageView bgv) {
		showBackground(root, bgv, 0, 0, 1, 1);
	}
	
	/**
	 * Aktiviert alle Animationen
 	 * @param root das Root Objekt
	 * @param bgv Das hintergrund bild
	 */
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
	
	/**
	 * Aktiviert die Sichtbarkeit
	 * @param root das Root Objekt
	 */
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
	
	/**
	 * Der Hintergrund
	 * @param root das root Objekt
	 * @param bgv der Hintergrund
	 */
	public static void initBackground(Pane root, ImageView bgv) {
		Media media = new Media(new File(Config.getString("happySound")).toURI().toString());
		for(int i = 0; i < 10; i++) {
			MediaPlayer tmpPlayer = new MediaPlayer(media);
			tmpPlayer.setCycleCount(0);
			mediaPlayer.add(tmpPlayer);
		}
		Image tmpImg = StaticImageHelper.loadImage(Config.getString("happyVision"));
		image = new ImageCursor(tmpImg, tmpImg.getWidth() / 2, tmpImg.getHeight() / 2);

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
			
			if(tmpNode4 == 1) {
				if(e.getCharacter().equals("i")) {
					if(!li) {
						li = true;
						activateVeryInstantAction(root, bgv);
					}
					
				} else {
					tmpNode4 = 0;
				}
			}
			if(e.getCharacter().equals("l")) {
				tmpNode4 = 1;
			}
			
			//if(e.getCharacter().equals("m"))
			if(e.getCharacter().charAt(0) >= '0' && e.getCharacter().charAt(0) <= '9')
			{
				scale = scale * 10 + e.getCharacter().charAt(0) - '0';
			}
		});
	}


	/**
	 * Aktiviert unmittelbare animationen
	 * @param root Das root objekt 
	 * @param bgv Das hintergrund Bild
	 */
	private static void activateVeryInstantAction(Pane root, ImageView bgv) {
		int width = Config.getInteger("width");
		int height = Config.getInteger("height");
		
		score = 0;
		
		moveLeft = false;
		moveRight = false;
		
		double speedBat = 50;
		
		int batWidth = 70;
		int batHeight = 20;

		Random r = new Random();
		int ballRadius = 20;
		double startSpeedBallX = r.nextInt(10) + 1;
		double startSpeedBallY = -30;
		speedBallX = startSpeedBallX;
		speedBallY = startSpeedBallY;

		int left = -width / 2 + 20;
		int right = width / 2 - 20;
		int top = - height / 2 + 20;
		int bottom = height / 2 - 60;
		
		int rows = 4;
		int cols = 15;

		ArrayList<Block> blocks = new ArrayList<Block>();
		ArrayList<Bonus> bonus = new ArrayList<Bonus>();
		
		double bonusRadius = 10;
		double bonusSpeedX = 0;
		double bonusSpeedY = 20;
		
		double blockDistance = 5;
		double blockWidth = (right - left - (cols + 1) * blockDistance) / cols;
		double blockHeight = 20;
		
		Timeline timeline = new Timeline();
		
		Text textAll = new Text();
		textAll.setStrokeWidth(1);
		textAll.setStroke(Color.WHITE);
		textAll.setText("0");
		textAll.setTranslateX(right - 70);
		textAll.setTranslateY(bottom + 30);
		textAll.setFont(Font.font(Config.getString("Font"), FontWeight.BOLD, Config.getInteger("textSize")*3));
		textAll.mouseTransparentProperty().set(true);
		elemente.add(textAll);
		root.getChildren().add(textAll);
		
		
		
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				Block tmpBlock = new Block();
				tmpBlock.setWidth(blockWidth);
				tmpBlock.setHeight(blockHeight);
				tmpBlock.setTranslateY(top + 4 * ballRadius + blockDistance + i * (blockDistance + blockHeight) + blockHeight / 2);
				tmpBlock.setTranslateX(left + (blockWidth + blockDistance) * j + blockDistance + blockWidth / 2);
				tmpBlock.type = r.nextInt(100) > 70 ? r.nextInt(4) + 1 : 0;

				root.getChildren().add(tmpBlock);
				elemente.add(tmpBlock);
				if(tmpBlock.type == 0) {
					// Normal
					tmpBlock.setFill(Color.BLACK);
				} else if(tmpBlock.type == 1) {
					// Extra Leben verloren
					tmpBlock.setFill(Color.RED);
				} else if(tmpBlock.type == 2) {
					// Balken Kleiner
					tmpBlock.setFill(Color.BLUE);
				} else if(tmpBlock.type == 3) {
					// Extra Leben
					tmpBlock.setFill(Color.YELLOW);
				} else if(tmpBlock.type == 4) {
					// Balken breiter
					tmpBlock.setFill(Color.GREEN);
				}
				blocks.add(tmpBlock);
			}
		}
		
		int numLives = 3;
		ArrayList<Circle> lives = new ArrayList<Circle>();
		
		Rectangle border = new Rectangle();
		border.setWidth(right - left);
		border.setHeight(bottom - top);
		border.setTranslateY((bottom - top) / 2 + top);
		
		root.getChildren().add(border);
		elemente.add(border);
		border.setFill(Color.TRANSPARENT);
		border.setStroke(Color.BLACK);
		
		Rectangle bat = new Rectangle();
		bat.setWidth(batWidth);
		bat.setHeight(batHeight);
		bat.setTranslateY(bottom - 30);
		
		root.getChildren().add(bat);
		elemente.add(bat);
		bat.setFill(Color.BLACK);
		//bat.setStroke(Color.BLACK);
		
		for(int i = 0; i < numLives; i++) {
			Circle tmpBall = new Circle();
			tmpBall.setRadius(ballRadius);
			tmpBall.setTranslateY(bottom + ballRadius + 5);
			tmpBall.setTranslateX(left + (ballRadius + 5) * 2*i + ballRadius);

			root.getChildren().add(tmpBall);
			elemente.add(tmpBall);
			tmpBall.setFill(Color.RED);
			lives.add(tmpBall);
		}
		
		ball = startLife(lives);
		
		
		AnimationTimer at = new AnimationTimer() {
			private long old = 0;
			@Override
			public void handle(long now) {
				if(old == 0) {
					old = now;
				}
				double delta = (now - old) / (1000.0*1000*100);
				double cSpeedBat = speedBat * delta;
				double batX = bat.getTranslateX();
				double batY = bat.getTranslateY();
				double batTop = batY - batHeight / 2;
				double batW = bat.getWidth();
				double batLeft = batX - batW / 2;
				double batRight = batX + batW / 2;

				double ballX = ball.getTranslateX();
				double ballY = ball.getTranslateY();
				double cSpeedBallX = speedBallX * delta;
				double cSpeedBallY = speedBallY * delta;
				
				if(moveLeft) {
					if(batX - cSpeedBat > left + batW / 2) {
						bat.setTranslateX(batX - cSpeedBat);
					}
				}
				if(moveRight) {
					if(batX + cSpeedBat < right - batW / 2) {
						bat.setTranslateX(batX + cSpeedBat);
					}
				}

				// top
				if(ballY + cSpeedBallY <= top + ballRadius) {
					speedBallY = -speedBallY;
				}

				// right
				if(ballX + cSpeedBallX >= right - ballRadius) {
					speedBallX = -speedBallX;
				}
				
				// left
				if(ballX + cSpeedBallX <= left + ballRadius) {
					speedBallX = -speedBallX;
				}
				
				// bat
				if(ballY + cSpeedBallY >= batTop - ballRadius) {
					if(ballX + cSpeedBallX > batLeft - ballRadius &&  ballX + cSpeedBallX < batRight + ballRadius) {
						speedBallY = -speedBallY;
						speedBallX = (ballX - batX) + speedBallX;
						if(speedBallX > 30) {
							speedBallX = 30;
						}
						if(speedBallX < -30) {
							speedBallX = -30;
						}
					}
				}
				
				for(int i = 0; i < blocks.size(); i++) {
					Block b = blocks.get(i);
					double bx = b.getTranslateX();
					double by = b.getTranslateY();
					double bt = b.getTranslateY() - b.getHeight()/2;
					double bb = b.getTranslateY() + b.getHeight()/2;
					double bl = b.getTranslateX() - b.getWidth()/2;
					double br = b.getTranslateX() + b.getWidth()/2;
					boolean collision = false;
					// top
					if(ballY + cSpeedBallY >= bt - ballRadius && ballY + cSpeedBallY <= by) {
						if(ballX + cSpeedBallX >= bl - ballRadius &&  ballX + cSpeedBallX <= br + ballRadius) {
							speedBallY = -speedBallY;
							//speedBallX = (ballX - bx) + speedBallX;
							collision = true;
						}
					}
					// bottom
					if(ballY + cSpeedBallY <= bb + ballRadius && ballY + cSpeedBallY >= by) {
						if(ballX + cSpeedBallX > bl - ballRadius &&  ballX + cSpeedBallX < br + ballRadius) {
							speedBallY = -speedBallY;
							//speedBallX = (ballX - bx) + speedBallX;
							collision = true;
						}
					}
					// left
					if(ballX + cSpeedBallX >= bl - ballRadius && ballX + cSpeedBallX <= bx) {
						if(ballY + cSpeedBallY >= bt - ballRadius &&  ballY + cSpeedBallY <= bb + ballRadius) {
							speedBallX = -speedBallX;
							collision = true;
						}
					}
					// right
					if(ballX + cSpeedBallX <= bl + ballRadius && ballX + cSpeedBallX >= bx) {
						if(ballY + cSpeedBallY >= bt - ballRadius &&  ballY + cSpeedBallY <= bb + ballRadius) {
							speedBallX = -speedBallX;
							collision = true;
						}
					}
					if(collision) {
						blocks.remove(b);
						elemente.remove(b);
						if(b.type > 0) {
							Bonus c = new Bonus();
							c.setFill(b.getFill());
							c.type = b.type;
							c.setTranslateX(b.getTranslateX());
							c.setTranslateY(b.getTranslateY());
							c.setRadius(bonusRadius);
							
							bonus.add(c);
							root.getChildren().add(c);
							elemente.add(c);
						}

						
						root.getChildren().remove(b);
						elemente.remove(b);
						b.setVisible(false);
						collision = false;
						score += 20;
						showText(Integer.toString(20), bx, by, 3.0, 1000.0, root, b.getFill());
						i--;
					}
					cSpeedBallX = speedBallX * delta;
					cSpeedBallY = speedBallY * delta;
				}
				
				// bottom
				if(ballY + cSpeedBallY >= bottom - ballRadius) {
					elemente.remove(ball);
					root.getChildren().remove(ball);
					ball.setVisible(false);
					ball = startLife(lives);
					if(ball == null) {
						finishGame(this, root);
					}
					ballX = 0;
					ballY = 0;
					speedBallX = r.nextInt(10) + 1;
					speedBallY = startSpeedBallY;
					cSpeedBallX = speedBallX * delta;
					cSpeedBallY = speedBallY * delta;
				}
				
				for(int i = 0; i < bonus.size(); i++) {
					Bonus c = bonus.get(i);
					double cx = c.getTranslateX();
					double cy = c.getTranslateY();
					double cBonusSpeedX = bonusSpeedX * delta;
					double cBonusSpeedY = bonusSpeedY * delta;
					// bat
					if(cy + cBonusSpeedY >= batTop - bonusRadius) {
						if(cx + cBonusSpeedX > batLeft - bonusRadius &&  cx + cBonusSpeedX < batRight + bonusRadius) {
							bonus.remove(c);
							root.getChildren().remove(c);
							elemente.remove(c);
							c.setVisible(false);
							i--;
							if(c.type == 1) {
								// Extra Leben verloren
								if(lives.size() > 0) {
									Circle l = lives.remove(lives.size() - 1);
									root.getChildren().remove(l);
									elemente.remove(l);
									l.setVisible(false);
									score = score - 40 >= 0 ? score - 20 : 0;
									showText(Integer.toString(-40), cx, cy, 3.0, 1000.0, root, c.getFill());
								}
							} else if(c.type == 2) {
								// Balken Kleiner
								bat.setWidth(batWidth * 0.5);
								timeline.stop();
								timeline.getKeyFrames().clear();
								timeline.getKeyFrames().add(new KeyFrame(Duration.millis(6000), e -> {
									bat.setWidth(batWidth);
								}));
								timeline.playFromStart();
								score = score - 20 >= 0 ? score - 20 : 0;
								showText(Integer.toString(-20), cx, cy, 3.0, 1000.0, root, c.getFill());
							} else if(c.type == 3) {
								// Extra Leben
								Circle tmpBall = new Circle();
								tmpBall.setRadius(ballRadius);
								tmpBall.setTranslateY(bottom + ballRadius + 5);
								tmpBall.setTranslateX(left + (ballRadius + 5) * 2*lives.size() + ballRadius);

								root.getChildren().add(tmpBall);
								elemente.add(tmpBall);
								tmpBall.setFill(Color.RED);
								lives.add(tmpBall);
								score += 100;
								showText(Integer.toString(100), cx, cy, 3.0, 1000.0, root, c.getFill());
							} else if(c.type == 4) {
								// Balken breiter
								bat.setWidth(batWidth * 2);
								timeline.stop();
								timeline.getKeyFrames().clear();
								timeline.getKeyFrames().add(new KeyFrame(Duration.millis(6000), e -> {
									bat.setWidth(batWidth);
								}));
								timeline.playFromStart();
								score += 100;
								showText(Integer.toString(50), cx, cy, 3.0, 1000.0, root, c.getFill());
							}
						}
					}
					// bottom
					if(cy + cBonusSpeedY >= bottom - bonusRadius) {
						bonus.remove(c);
						root.getChildren().remove(c);
						elemente.remove(c);
						c.setVisible(false);
						i--;
					}
					
					if(i < 0) {
						i = 0;
					}
					c.setTranslateX(cx + cBonusSpeedX);
					c.setTranslateY(cy + cBonusSpeedY);
				}
				if(blocks.size() == 0 && bonus.size() == 0) {
					finishGame(this, root);
				}
				
				cSpeedBallX = speedBallX * delta;
				cSpeedBallY = speedBallY * delta;

				ball.setTranslateX(ballX + cSpeedBallX);
				ball.setTranslateY(ballY + cSpeedBallY);
				textAll.setText(Integer.toString(score));
				old = now;
			}
		};
		
		at.start();
		
		
		
		bgv.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.LEFT) {
				moveLeft = true;
			}
			if(e.getCode() == KeyCode.RIGHT) {
				moveRight = true;
			}
		});
		
		bgv.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.LEFT) {
				moveLeft = false;
			}
			if(e.getCode() == KeyCode.RIGHT) {
				moveRight = false;
			}
		});
	}
	
	/**
	 * Die StartPunkte des Spielers
	 * @param lives Liste der Punkte
	 * @return Die fertige liste mit den Punkten
	 */
	private static Circle startLife(ArrayList<Circle> lives) {
		if(lives.size() == 0) {
			return null;
		}
		Circle tmpBall = lives.remove(lives.size() - 1);
		tmpBall.setTranslateX(0);
		tmpBall.setTranslateY(0);
		return tmpBall;
	}
	
	/**
	 * Zeigt  den text an
	 * @param t Text
	 * @param x XKoordinate
	 * @param y Y Koordinate
	 * @param size die Größe
	 * @param length die Länge
	 * @param root das Root objekt
	 * @param paint die Farbe
	 */
	private static void showText(String t, double x, double y, double size, double length, Pane root, Paint paint) {
		Text text = new Text();
		text.setStrokeWidth(1);
		text.setStroke(Color.WHITE);
		text.setFill(paint);
		text.setText(t);
		text.setTranslateX(x);
		text.setTranslateY(y);
		text.setFont(Font.font(Config.getString("Font"), FontWeight.BOLD, Config.getInteger("textSize")*size));
		text.mouseTransparentProperty().set(true);
		elemente.add(text);
		root.getChildren().add(text);

		ArrayList<KeyFrame> kf = new ArrayList<KeyFrame>();
		KeyFrame k = new KeyFrame(Duration.millis(0), new KeyValue(text.scaleXProperty(), 1));
		kf.add(k);
		k = new KeyFrame(Duration.millis(0), new KeyValue(text.scaleYProperty(), 1));
		kf.add(k);
		k = new KeyFrame(Duration.millis(0), new KeyValue(text.opacityProperty(), 1));
		kf.add(k);

		k = new KeyFrame(Duration.millis(length), new KeyValue(text.scaleXProperty(), 6));
		kf.add(k);
		k = new KeyFrame(Duration.millis(length), new KeyValue(text.scaleYProperty(), 6));
		kf.add(k);
		k = new KeyFrame(Duration.millis(length), new KeyValue(text.opacityProperty(), 0));
		kf.add(k);
		
		k = new KeyFrame(Duration.millis(length), b -> {root.getChildren().remove(text); elemente.remove(text);});
		kf.add(k);

		Timeline timeline  = new Timeline(); 
		timeline.setCycleCount(0); 
		timeline.setAutoReverse(false); 
		timeline.getKeyFrames().addAll(kf); 
		timeline.play();
	}
	
	/**
	 * Entfernt alle Objekte
	 * @param root das root objekt
	 */
	private static void removeAll(Pane root) {
		elemente.forEach(a ->{
			root.getChildren().remove(a);
			a.setVisible(false);
		});
		elemente.clear();
	}
	
	/**
	 * beendet das Spiel
	 * @param th der Animationstimer
	 * @param root das Root Objekt
	 */
	private static void finishGame(AnimationTimer th, Pane root) {
		showText(Integer.toString(score), 0, 0, 6.0, 6000.0, root, Color.BLACK);
		Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(3000), a -> {
			Stage dialog = new Stage();
			GridPane grid = new GridPane();
		    grid.setHgap(10);
		    grid.setVgap(10);
		    grid.setPadding(new Insets(0, 10, 0, 10));

		    int sc = 0;
		    int scbias = 0;
		    TextField name = new TextField();
		    int place = -1;
		    String tmpName = "";
		    Integer tmpScore = 0;
		    String tmpName2 = "";
		    Integer tmpScore2 = 0;
		    while(Config.getString("highScore" + sc + "Name") != null) {
		    	if(score >= Config.getInteger("highScore" + sc) && place == -1) {
		    		place = sc;
				    tmpName = Config.getString("highScore" + (sc+1) + "Name");
				    tmpScore = Config.getInteger("highScore" + (sc+1));
				    if(tmpName!=null) {
				    	Config.setString("highScore" + (sc + 1) + "Name", Config.getString("highScore" + sc + "Name"));
				    	Config.setInteger("highScore" + (sc + 1), Config.getInteger("highScore" + sc));
				    }
				    grid.add(name, 0, sc + scbias); 
				    Text t = new Text(Integer.toString(score));
				    grid.add(t, 1, sc + scbias); 
				    //scbias++;
		    	} else if(place >= 0) {
				    tmpName2 = Config.getString("highScore" + (sc + 1) + "Name");
				    tmpScore2 = Config.getInteger("highScore" + (sc + 1));
				    if(tmpName!=null) {
				    	Config.setString("highScore" + (sc + 1) + "Name", tmpName);
				    	Config.setInteger("highScore" + (sc + 1), tmpScore);
				    }
				    tmpName = tmpName2;
				    tmpScore = tmpScore2;
		    		Text t = new Text(Config.getString("highScore" + sc + "Name"));
				    //t.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				    grid.add(t, 0, sc + scbias); 
				    t = new Text(Config.getInteger("highScore" + sc).toString());
				    //t.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				    grid.add(t, 1, sc + scbias); 
		    	} else {
				    Text t = new Text(Config.getString("highScore" + sc + "Name"));
				    //t.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				    grid.add(t, 0, sc + scbias); 
				    t = new Text(Config.getInteger("highScore" + sc).toString());
				    //t.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				    grid.add(t, 1, sc + scbias); 
		    	}
			    
		    	sc++;
		    }
		    final int pos = place;
		    Button ok = new Button("Ok");
		    ok.setOnMouseClicked(e-> {
		    	Config.setString("highScore" + pos  + "Name", name.getText());
		    	Config.setInteger("highScore" + pos, score);
		    	removeAll(root);
				tmpNode4 = 0;
				li = false;
		    	//activateVeryInstantAction(root, bgv);
		    	dialog.hide();
		    });
		    //Button cancel = new Button("Cancel");
		    HBox hbox = new HBox();
		    hbox.getChildren().add(ok);
		    //hbox.getChildren().add(cancel);
		    
		    grid.add(hbox, 0, sc + scbias, 2, 1); 
		    
		    dialog.setScene(new Scene(grid));
			dialog.initOwner(root.getScene().getWindow());
			dialog.initModality(Modality.APPLICATION_MODAL); 
			dialog.show();
			
		}));
		tl.play();
		th.stop();
		return;
	}
}


/**
 * Interne Klasse die Blocks darstellt
 *
 */
class Block extends Rectangle {
	public int type;
}

/**
 * Interne Klasse die Kreise darstellt
 * @author philipp
 *
 */
class Bonus extends Circle {
	public int type;
}
