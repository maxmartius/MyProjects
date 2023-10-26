public class Punkt {
	
	private double x;
	private double y;
	
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
	
	public String ausgabe() {
		String p = "("+x+" , "+y+")";
		return p;
	}

}
