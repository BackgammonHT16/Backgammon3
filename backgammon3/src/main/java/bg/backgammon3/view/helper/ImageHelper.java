/**
 * 
 */
package bg.backgammon3.view.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.view.BoardView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author philipp
 *
 */
public class ImageHelper extends ImageView {
	private Logger logger = LogManager.getLogger(ImageHelper.class);
	public ImageHelper(String filename)
	{
		LoadImage(filename);
	}
	public ImageHelper(String filename, int width)
	{
		LoadImage(filename, width);
	}
	public ImageHelper(String filename, int width, boolean isClickable)
	{
		LoadImage(filename, width, isClickable);
	}
	
	public ImageHelper(Image image, int width, boolean isClickable)
	{
		LoadImage(image, width, isClickable);
	}
	
	public ImageHelper(
			String filename, 
			Integer width, 
			boolean isClickable, 
			Double x, 
			Double y)
	{
		LoadImage(filename, width, isClickable, x, y);
	}
	
	public ImageHelper(
			String filename, 
			Integer width, 
			boolean isClickable, 
			Integer x, 
			Integer y)
	{
		LoadImage(filename, width, isClickable, (double) x.intValue(), (double) y.intValue());
	}
	
	private void LoadImage(String filename)
	{
		StaticImageHelper.loadImageView(filename, this);
	}

	private void LoadImage(String filename, int width)
	{
		LoadImage(filename);
		setFitWidth(width);
	}

	private void LoadImage(String filename, int width, boolean isClickable)
	{
		LoadImage(filename, width);
		setMouseTransparent(!isClickable);
	}

	private void LoadImage(Image image, int width, boolean isClickable)
	{
		setImage(image);
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
		setFitWidth(width);
		setMouseTransparent(!isClickable);
	}
	
	private void LoadImage(
			String filename, 
			int width, 
			boolean isClickable, 
			Double x, 
			Double y)
	{
		LoadImage(filename, width, isClickable);
		setTranslateX(x);
		setTranslateY(y);
	}
}
