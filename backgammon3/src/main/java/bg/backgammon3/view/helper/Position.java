/**
 * 
 */
package bg.backgammon3.view.helper;

/**
 * Position
 *
 */
public class Position {
	public double x;
	public double y;
	public double rotation;
	
	/**
	 * Konstruktor
	 */
	public Position()
	{
		
	}

	/**
	 * Konstruktor
	 * @param x die X Position
	 * @param y die Y Position
	 */
	public Position(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.rotation = 0;
	}

	/**
	 * Konstruktor
	 * @param x die X Position
	 * @param y die Y Position
	 * @param rotation Die Rotation
	 */
	public Position(double x, double y, double rotation)
	{
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
}
