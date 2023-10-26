package application;

import java.util.List;
import java.util.ArrayList;

public class Generator {

	private Initiator initiator;
	private List<Linie> linien;
	
	/**
	 * Generator der Kochkurven.
	 * @param initiator -> Initiator der Kochkurven
	 * @param linien -> stellen den Generator da.
	 */
	
	public Generator(Initiator initiator, List<Linie> linien) {
		this.initiator = initiator;
		this.linien = linien;
	}
	
	/**
	 * Generator der Kochkurven.
	 * @param initiator -> Initiator der Kochkurven
	 * @param i -> ausswahl aus den gegebenen Bsp. Generatoren.
	 */
	
	public Generator(Initiator initiator, int i) {
		this.initiator = initiator;
		if (i == 1) {
			generator1();
		} else if (i == 2) {
			generator2();
		} else if (i == 3){
			generator3();
		}
	}
	
	/**
	 * einmalige Iteration über alle Linien des Initiators.
	 */
	
	public void generiereSchrittweise() {
		List<Linie> rst = new ArrayList<Linie>();
		for (Linie linie : initiator.getLinien()) {
			double lverhaeltnis = linie.getLaenge();
			Punkt anfang = linie.getUrsprungsPunkt();
			for (Linie l : linien) {
				double grad = ((l.getGrad() + linie.getGrad()) % 360);
				double laenge = (l.getLaenge() * lverhaeltnis);
				Linie ln = new Linie(anfang, laenge, grad);
				rst.add(ln);
				anfang = ln.getEndPunkt();
			}

		}
		this.initiator.setLinien(rst);
	}
	
	/**
	 * Iteriet sollange ueber die Linien, bis die minimale Laenge erreicht ist. 
	 * @param minLaenge -> minimale Laenge der Linien.
	 */
	
	public void generiereMinLaenge(double minLaenge) {
		while (initiator.getLinien().get(0).getLaenge() > minLaenge) {
			generiereSchrittweise();
		}
	}
	
	/**
	 * Generator der "Dreiecksform".
	 */
	
	private void generator1() {
		Punkt u1 = new Punkt(0, 0);
		Punkt e1 = new Punkt(1 / 3.0, 0);

		Punkt u2 = new Punkt(1 / 3.0, 0);
		Punkt e2 = new Punkt(1 / 2.0, (1 / 3.0 * (Math.sin(Math.toRadians(60)))));

		Punkt u3 = new Punkt(1 / 2.0, (1 / 3.0 * (Math.sin(Math.toRadians(60)))));
		Punkt e3 = new Punkt(2 / 3.0, 0);

		Punkt u4 = new Punkt(2 / 3.0, 0);
		Punkt e4 = new Punkt(1, 0);

		Linie l1 = new Linie(u1, e1);
		Linie l2 = new Linie(u2, e2);
		Linie l3 = new Linie(u3, e3);
		Linie l4 = new Linie(u4, e4);
		List<Linie> linien = new ArrayList<Linie>();

		linien.add(l1);
		linien.add(l2);
		linien.add(l3);
		linien.add(l4);
		this.linien = linien;
	}
	
	/**
	 * Generator der "Zackenform".
	 */
	
	private void generator2() {
		Punkt u1 = new Punkt(0, 0);
		Punkt e1 = new Punkt(0.4, 0.2);

		Punkt u2 = new Punkt(0.4, 0.2);
		Punkt e2 = new Punkt(0.6, -0.2);

		Punkt u3 = new Punkt(0.6, -0.2);
		Punkt e3 = new Punkt(1.0, 0);

		Linie l1 = new Linie(u1, e1);
		Linie l2 = new Linie(u2, e2);
		Linie l3 = new Linie(u3, e3);
		List<Linie> linien = new ArrayList<Linie>();

		linien.add(l1);
		linien.add(l2);
		linien.add(l3);
		this.linien = linien;
	}
	
	/**
	 * Generator der "Vierecksform".
	 */
	private void generator3() {
		Punkt u1 = new Punkt(0, 0);
		Punkt e1 = new Punkt(1 / 3.0, 0);

		Punkt u2 = new Punkt(1 / 3.0, 0);
		Punkt e2 = new Punkt(1 / 3.0, 1 / 3.0);

		Punkt u3 = new Punkt(1 / 3.0, 1 / 3.0);
		Punkt e3 = new Punkt(2 / 3.0, 1 / 3.0);

		Punkt u4 = new Punkt(2 / 3.0, 0);
		Punkt e4 = new Punkt(1, 0);

		Linie l1 = new Linie(u1, e1);
		Linie l2 = new Linie(u2, e2);
		Linie l3 = new Linie(u3, e3);
		Linie l3b = new Linie (e3, u4);
		Linie l4 = new Linie(u4, e4);
		List<Linie> linien = new ArrayList<Linie>();

		linien.add(l1);
		linien.add(l2);
		linien.add(l3);
		linien.add(l3b);
		linien.add(l4);
		this.linien = linien;
	}

	public Initiator getInitiator() {
		return initiator;
	}
}
