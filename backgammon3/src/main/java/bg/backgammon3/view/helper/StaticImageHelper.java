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
 * @author philipp
 *
 */
public class StaticImageHelper {
	
	// Dadurch muss jedes Bild, insbesondere die SVGs nur einmal geladen werden.
	private static LinkedHashMap<String, Image> images = new LinkedHashMap<String, Image>();
	private static Logger logger = LogManager.getLogger(StaticImageHelper.class);

	public static Image loadImage(String filename) {
		logger.info("LoadImage mit filename: " + filename);
		if(images.get(filename) == null) {
			logger.info("Bild mit filename: " + filename + " wird zum ersten Mal geladen.");
			images.put(filename, new Image("file:" + filename));
		}
		return images.get(filename);
	}
	
	public static void loadImageView(String filename, ImageView image)
	{
		image.setImage(loadImage(filename));
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);
        image.setPickOnBounds(true);
	}

	public static void loadImageView(String filename, int width, ImageView image)
	{
		loadImageView(filename, image);
		image.setFitWidth(width);
	}

	public static void loadImageView(String filename, int width, boolean isClickable, ImageView image)
	{
		loadImageView(filename, width, image);
		image.setMouseTransparent(!isClickable);
	}

	public static void loadImageView(Image image, int width, boolean isClickable, ImageView imageView)
	{
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setCache(true);
		imageView.setFitWidth(width);
		imageView.setMouseTransparent(!isClickable);
	}
	
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
