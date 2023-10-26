package application;

public class Punkt {

	private double x;
	private double y;
	
	/**
	 * Punkt repraeseentiert einen Punkt im 2D-Raum.
	 * @param x -> X-Koordinate
	 * @param y -> Y-Koordinate
	 */
	public Punkt(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	
	/**
	 * Format: (x , y)
	 */
	
	@Override
	public String toString() {
		String p = "("+x+" , "+y+")";
		return p;
	}

}
