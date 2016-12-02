/**
 * 
 */
package bg.backgammon3.view.helper;

/**
 * Vector
 *
 */
public class MathVector {
	private Double x;
	private Double y;
	
	/**
	 * Konstruktor
	 */
	public MathVector() {
		x = 0d;
		y = 0d;
	}
	
	/**
	 * Konstruktor
	 * @param x X Koordinate
	 * @param y Y Koordinate
	 */
	public MathVector(Double x, Double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Konstruktor
	 * @param x X Koordinate
	 * @param y Y Koordinate
	 */
	public MathVector(Integer x, Integer y) {
		this.x = x.doubleValue();
		this.y = y.doubleValue();
	}
	
	/**
	 * Länge
	 * @return länge des Vektors
	 */
	public Double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * Normierter Vektor
	 * @return der Normierte Vektor
	 */
	public MathVector norm() {
		if(length() == 0) {
			return new MathVector(0d, 0d);
		}
		return new MathVector(x / length(), y / length());
	}
	
	/**
	 * Dreht den Vektor um den angegebenen Winkel
	 * @param angle der Winkel
	 * @return Der Gedrehte vektor
	 */
	public MathVector turn(Double angle) {
		Double nx = x * Math.cos(angle/180*Math.PI) - y * Math.sin(angle/180*Math.PI);
		Double ny = x * Math.sin(angle/180*Math.PI) + y * Math.cos(angle/180*Math.PI);
		return new MathVector(nx, ny);
	}

	/**
	 * Skaliert den Vektor
	 * @param s Der Faktor
	 * @return der Skalierte Vektor
	 */
	public MathVector scale(Double s) {
		return new MathVector(x * s, y * s);
	}

	/**
	 * Skaliert den Vektor
	 * @param s Der Faktor
	 * @return der Skalierte Vektor
	 */
	public MathVector scale(Integer s) {
		return scale(s.doubleValue());
	}
	
	/**
	 * gibt die x koordinate heraus
 	 * @return die X Koordinate
	 */
	public Double getX() {
		return new Double(x);
	}

	/**
	 * gibt die y koordinate heraus
 	 * @return die Y Koordinate
	 */
	public Double getY() {
		return new Double(y);
	}
	/**
	 * Addiert zwei vektoren
	 * @param v der andere vektor
	 * @return der neue vektor
	 */
	public MathVector add(MathVector v) {
		return new MathVector(x + v.x, y + v.y);
	}

	/**
	 * Subtrahiert zwei vektoren
	 * @param v der andere vektor
	 * @return der neue vektor
	 */
	public MathVector sub(MathVector v) {
		return new MathVector(x - v.x, y - v.y);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
