/**
 * 
 */
package bg.backgammon3.view.helper;

/**
 * 
 *
 */
public class MathVector {
	private Double x;
	private Double y;
	
	public MathVector() {
		x = 0d;
		y = 0d;
	}
	
	public MathVector(Double x, Double y) {
		this.x = x;
		this.y = y;
	}
	
	public MathVector(Integer x, Integer y) {
		this.x = x.doubleValue();
		this.y = y.doubleValue();
	}
	
	public Double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	public MathVector norm() {
		if(length() == 0) {
			return new MathVector(0d, 0d);
		}
		return new MathVector(x / length(), y / length());
	}
	
	public MathVector turn(Double angle) {
		Double nx = x * Math.cos(angle/180*Math.PI) - y * Math.sin(angle/180*Math.PI);
		Double ny = x * Math.sin(angle/180*Math.PI) + y * Math.cos(angle/180*Math.PI);
		return new MathVector(nx, ny);
	}

	public MathVector scale(Double s) {
		return new MathVector(x * s, y * s);
	}

	public MathVector scale(Integer s) {
		return scale(s.doubleValue());
	}
	
	public Double getX() {
		return new Double(x);
	}
	
	public Double getY() {
		return new Double(y);
	}

	public MathVector add(MathVector v) {
		return new MathVector(x + v.x, y + v.y);
	}
	
	public MathVector sub(MathVector v) {
		return new MathVector(x - v.x, y - v.y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
