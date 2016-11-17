/**
 * 
 */
package bg.backgammon3.view.helper;

/**
 * 
 *
 */
public class Position {
	public double x;
	public double y;
	public double rotation;
	
	public Position()
	{
		
	}

	public Position(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.rotation = 0;
	}
	
	public Position(double x, double y, double rotation)
	{
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
}
