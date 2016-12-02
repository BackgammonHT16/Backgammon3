/**
 * 
 */
package bg.backgammon3.view.helper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Der ImageHelper
 *
 */
public class ImageHelper extends ImageView {
	
	/**
	 * Konstruktor
	 * @param filename Datei name
	 */
	public ImageHelper(String filename)
	{
		LoadImage(filename);
	}
	/**
	 * Konstruktor
	 * @param filename das Bild
	 * @param width die Breite
	 */
	public ImageHelper(String filename, int width)
	{
		LoadImage(filename, width);
	}
	/**
	 * Konstruktor
	 * @param filename das Bild
	 * @param width die Breite
	 * @param isClickable wahr wenn anklclickbar
	 */
	public ImageHelper(String filename, int width, boolean isClickable)
	{
		LoadImage(filename, width, isClickable);
	}
	
	/**
	 * Konstruktor
	 * @param image das Bild
	 * @param width die Breite
	 * @param isClickable wahr wenn anklclickbar
	 */
	public ImageHelper(Image image, int width, boolean isClickable)
	{
		LoadImage(image, width, isClickable);
	}
	
	/**
	 * Lädt das Bild
	 * @param filename Dateiname
	 * @param width Breite
	 * @param isClickable anklickbar
	 * @param x X Koordinate
	 * @param y Y Koordinate
	 */
	public ImageHelper(
			String filename, 
			Integer width, 
			boolean isClickable, 
			Double x, 
			Double y)
	{
		LoadImage(filename, width, isClickable, x, y);
	}
	
	/**
	 * Lädt das Bild
	 * @param filename Dateiname
	 * @param width breite
	 * @param isClickable ist anclickbar
	 * @param x X Koordinate
	 * @param y Y Koordinate
	 */
	public ImageHelper(
			String filename, 
			Integer width, 
			boolean isClickable, 
			Integer x, 
			Integer y)
	{
		LoadImage(filename, width, isClickable, (double) x.intValue(), (double) y.intValue());
	}

	/**
	 * Lädt das Bild
	 * @param filename Dateiname
	 */
	private void LoadImage(String filename)
	{
		StaticImageHelper.loadImageView(filename, this);
	}

	/**
	 * Lädt das Bild
	 * @param filename Dateiname
	 * @param width breite
	 */
	private void LoadImage(String filename, int width)
	{
		LoadImage(filename);
		setFitWidth(width);
	}

	/**
	 * Lädt das Bild
	 * @param filename Dateiname
	 * @param width breite
	 * @param isClickable ist Anclickbar
	 */
	private void LoadImage(String filename, int width, boolean isClickable)
	{
		LoadImage(filename, width);
		setMouseTransparent(!isClickable);
	}

	/**
	 * Lädt das Bild
	 * @param image Bild
	 * @param width breite
	 * @param isClickable ist Anclickbar
	 */
	private void LoadImage(Image image, int width, boolean isClickable)
	{
		setImage(image);
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
		setFitWidth(width);
		setMouseTransparent(!isClickable);
	}
	
	/**
	 * Lädt das Bild
	 * @param filename Dateiname
	 * @param width breite
	 * @param isClickable ist Anclickbar
	 * @param x X Wert
	 * @param y Y Wert
	 */
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
