package application;

public class Linie {
	
	private Punkt ursprungsPunkt;
	private Punkt endPunkt;
	private double laenge;
	private double grad;
	
	/**
	 * gibt eine Linie vom Ursprungs- zum Endpunkt im 2D-Raum..
	 * @param ursprungsPunkt -> Startpunkt.
	 * @param endPunkt -> Endpunkt.
	 */
	
	public Linie(Punkt ursprungsPunkt, Punkt endPunkt) {
		this.ursprungsPunkt = ursprungsPunkt;
		this.endPunkt = endPunkt;
		berechneLeange();
		berechneGrad();
	}
	
	/**
	 * gibt eine Linie vom Ursprungs- zum Endpunkt im 2D-Raum, wobei der Endpunkt als Polarkoordinate durch "grad" und "laenge" gegeben ist.
	 * @param ursprungsPunkt -> Startpunkt.
	 * @param laenge ->Entfernung zwischen Sart- und Endpunkt.
	 * @param grad -> Winkel zwischen der Linie und der Horizontalen.
	 */
	
	public Linie(Punkt ursprungsPunkt, double laenge, double grad) {
		this.ursprungsPunkt = ursprungsPunkt;
		this.laenge = laenge;
		this.grad = grad;
		berechneEndPunkt();
	}
	
	/**
	 * berechnet die Laenge einer Linie.
	 */
	
	private void berechneLeange() {
		double a = this.ursprungsPunkt.getX() - this.endPunkt.getX();
		double b = this.ursprungsPunkt.getY() - this.endPunkt.getY();
		this.laenge = (Math.sqrt((a * a) + (b * b)));
	}
	
	/**
	 * berechnet den Winkel zwischen der Linie und der Horizontalen.
	 */
	
	private void berechneGrad() {
		double x = this.ursprungsPunkt.getX() - this.endPunkt.getX();
		double y = this.ursprungsPunkt.getY() - this.endPunkt.getY();
		x = Math.sqrt(x * x);
		y = Math.sqrt(y * y);
		double rad = Math.atan(y / x);
		if (this.ursprungsPunkt.getX() <= this.endPunkt.getX()) {
			if (this.ursprungsPunkt.getY() <= this.endPunkt.getY()) {
				this.grad = Math.toDegrees(rad);
			} else {
				this.grad = 360 - Math.toDegrees(rad);
			}
		} else {
			if (this.ursprungsPunkt.getY() <= this.endPunkt.getY()) {
				this.grad = 180 - Math.toDegrees(rad);
			} else {
				this.grad = 180 + Math.toDegrees(rad);
			}
		}

	}
	
	/**
	 * berechnet den Entpunkt im Karthesischem Koordinatensystem.
	 */
	
	private void berechneEndPunkt() {
		double g;
		if (this.grad <= 90) {
			g = this.grad;
		} else if (this.grad <= 180) {
			g = 180 - this.grad;
		} else if (this.grad <= 270) {
			g = this.grad - 180;
		} else {
			g = 360 - this.grad;
		}
		double rad = Math.toRadians(g);
		double x = this.laenge * (Math.cos(rad));
		double y = this.laenge * (Math.sin(rad));
		x = Math.sqrt(x * x);
		x = Math.sqrt(x * x);
		if (this.grad <= 90) {
			x += this.ursprungsPunkt.getX();
			y += this.ursprungsPunkt.getY();
		} else if (this.grad <= 180) {
			x = this.ursprungsPunkt.getX() - x;
			y += this.ursprungsPunkt.getY();
		} else if (this.grad <= 270) {
			x = this.ursprungsPunkt.getX() - x;
			y = this.ursprungsPunkt.getY() - y;
		} else {
			x += this.ursprungsPunkt.getX();
			y = this.ursprungsPunkt.getY() - y;
		}
		Punkt endPunkt = new Punkt(x, y);
		this.endPunkt = endPunkt;

	}

	public double getGrad() {
		return this.grad;
	}

	public double getLaenge() {
		return this.laenge;
	}

	public Punkt getEndPunkt() {
		return this.endPunkt;
	}

	public Punkt getUrsprungsPunkt() {
		return this.ursprungsPunkt;
	}
	/**
	 * Fomat: (x1 ,y1) - (x2, y2)
	 */
	
	@Override
	public String toString() {
		String l = this.ursprungsPunkt.toString() + " - " + this.endPunkt.toString();
		return l;
	}

}
