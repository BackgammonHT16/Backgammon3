/**
 * 
 */
package bg.backgammon3.view.helper;


import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Ladet Bilder
 *
 */
public class StaticImageHelper {
	
	// Dadurch muss jedes Bild, insbesondere die SVGs nur einmal geladen werden.
	private static LinkedHashMap<String, Image> images = new LinkedHashMap<String, Image>();
	private static Logger logger = LogManager.getLogger(StaticImageHelper.class);


	/**
	 * Ladet Bild
	 * @param filename Bild
	 */
	public static Image loadImage(String filename) {
		logger.info("LoadImage mit filename: " + filename);
		if(images.get(filename) == null) {
			logger.info("Bild mit filename: " + filename + " wird zum ersten Mal geladen.");
			images.put(filename, new Image("file:" + filename));
		}
		return images.get(filename);
	}

	/**
	 * Ladet Bild
	 * @param filename Bild
	 * @param image Die zu benutzende imageView
	 */
	public static void loadImageView(String filename, ImageView image)
	{
		image.setImage(loadImage(filename));
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);
        image.setPickOnBounds(true);
	}
	/**
	 * Ladet Bild
	 * @param filename Bild
	 * @param width breite
	 * @param image Die zu benutzende imageView
	 */
	public static void loadImageView(String filename, int width, ImageView image)
	{
		loadImageView(filename, image);
		image.setFitWidth(width);
	}
	/**
	 * Ladet Bild
	 * @param filename Bild
	 * @param width breite
	 * @param isClickable ist anclickbar
	 * @param image Die zu benutzende imageView
	 */
	public static void loadImageView(String filename, int width, boolean isClickable, ImageView image)
	{
		loadImageView(filename, width, image);
		image.setMouseTransparent(!isClickable);
	}

	/**
	 * Ladet Bild
	 * @param image Bild
	 * @param width breite
	 * @param isClickable ist anclickbar
	 * @param imageView Die zu benutzende imageView
	 */
	public static void loadImageView(Image image, int width, boolean isClickable, ImageView imageView)
	{
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setCache(true);
		imageView.setFitWidth(width);
		imageView.setMouseTransparent(!isClickable);
	}

	/**
	 * Ladet Bild
	 * @param filename Dateiname
	 * @param width breite
	 * @param isClickable ist anclickbar
	 * @param x x Position
	 * @param y y Position 
	 * @param image Die zu benutzende imageView
	 */
	public static void loadImageView(
			String filename, 
			int width, 
			boolean isClickable, 
			Double x, 
			Double y,
			ImageView image)
	{
		loadImageView(filename, width, isClickable, image);
		image.setTranslateX(x);
		image.setTranslateY(y);
	}
	
	/**
	 * Ladet Bild
	 * @param filename Dateiname
	 * @param width breite
	 * @param isClickable ist anclickbar
	 * @param x x Position
	 * @param y y Position
	 * @param image Die zu benutzende imageView
	 */
	public static void loadImageView(
			String filename, 
			int width, 
			boolean isClickable, 
			Integer x, 
			Integer y,
			ImageView image)
	{
		loadImageView(filename, width, isClickable, (double) x.intValue(), (double) y.intValue(), image);
	}
}
