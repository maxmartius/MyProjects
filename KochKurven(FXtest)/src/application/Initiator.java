package application;

import java.util.ArrayList;
import java.util.List;

public class Initiator {

	private List<Linie> linien;
	
	/**
	 * Initiator der Kochkurven.
	 * @param linien -> Die linien vor der Iteration durch den Generator.
	 */
	public Initiator(List<Linie> linien) {
		this.linien = linien;
	}
	
	/**
	 * Initiator der Kochkurven.
	 * @param i -> ausswahl aus den gegebenen Bsp. Initiatoren.
	 */
	public Initiator(int i) {
		if (i == 1) {
			linie();
		} else if (i == 2) {
			quadratAussen();
		} else if (i == 3) {
			quadratInnen();
		} else if (i == 4) {
			dreieck();
		} else if (i == 5) {
			max();
		}

	}

	public List<Linie> getLinien() {
		return this.linien;
	}

	public void setLinien(List<Linie> linien) {
		this.linien = linien;
	}
	
	/**
	 * Initiator der Form einer Linie.
	 */
	
	private void linie() {
		Punkt uP = new Punkt(-0.5, 0.0);
		Punkt eP = new Punkt(0.5, 0.0);
		Linie l = new Linie(uP, eP);
		List<Linie> linien = new ArrayList<Linie>();
		linien.add(l);
		this.linien = linien;
	}

	/**
	 * Initiator der Form eines Quadrates, bei Anwendung des Generators zeigen die Linien so, dass die neuen nach aussen zeigen.
	 */
	
	private void quadratAussen() {
		Punkt uP1 = new Punkt(-0.5, 0.5);
		Punkt eP1 = new Punkt(0.5, 0.5);
		Linie l1 = new Linie(uP1, eP1);
		Punkt eP2 = new Punkt(0.5, -0.5);
		Linie l2 = new Linie(eP1, eP2);
		Punkt eP3 = new Punkt(-0.5, -0.5);
		Linie l3 = new Linie(eP2, eP3);
		Linie l4 = new Linie(eP3, uP1);
		List<Linie> linien = new ArrayList<Linie>();
		linien.add(l1);
		linien.add(l2);
		linien.add(l3);
		linien.add(l4);
		this.linien = linien;
	}

	/**
	 * Initiator der Form eines Quadrates, bei Anwendung des Generators zeigen die Linien so, dass die neuen nach innen zeigen.
	 */
	
	private void quadratInnen() {
		Punkt uP1 = new Punkt(0.5, 0.5);
		Punkt eP1 = new Punkt(-0.5, 0.5);
		Linie l1 = new Linie(uP1, eP1);
		Punkt eP2 = new Punkt(-0.5, -0.5);
		Linie l2 = new Linie(eP1, eP2);
		Punkt eP3 = new Punkt(0.5, -0.5);
		Linie l3 = new Linie(eP2, eP3);
		Linie l4 = new Linie(eP3, uP1);
		List<Linie> linien = new ArrayList<Linie>();
		linien.add(l1);
		linien.add(l2);
		linien.add(l3);
		linien.add(l4);
		this.linien = linien;
	}

	/**
	 * Initiator der Form eines gleichseitigem Dreieck.
	 */
	
	private void dreieck() {
		Punkt uP1 = new Punkt(0.0, (Math.sqrt(3) / 3.0));
		Linie l1 = new Linie(uP1, 1, 300);
		Linie l2 = new Linie(l1.getEndPunkt(), 1, 180);
		Linie l3 = new Linie(l2.getEndPunkt(), uP1);
		List<Linie> linien = new ArrayList<Linie>();
		linien.add(l1);
		linien.add(l2);
		linien.add(l3);
		this.linien = linien;
	}
	

	/**
	 * Initiator der Form des Wortes "MAX".
	 */
	
	private void max() {
		Punkt p1 = new Punkt(-1, 0);
		Punkt p2 = new Punkt(-1, 1);
		Punkt p3 = new Punkt(-0.5, 0.7);
		Punkt p4 = new Punkt(0, 1);
		Punkt p5 = new Punkt(0, 0);
		Punkt p5b = new Punkt(1, 0);
		Punkt p6 = new Punkt(1.5, 1);
		Punkt p7 = new Punkt(2, 0);
		Punkt p7b = new Punkt(3, 0);
		Punkt p8 = new Punkt(3.5, 0.5);
		Punkt p9 = new Punkt(4, 1);
		Punkt p10 = new Punkt(3, 1);
		Punkt p11 = new Punkt(3.5, 0.5);
		Punkt p12 = new Punkt(4, 0);
		Punkt pa1 = new Punkt(1.2, 0.333);
		Punkt pa2 = new Punkt(1.8, 0.333);
		Linie l1 = new Linie(p1, p2);
		Linie l2 = new Linie(p2, p3);
		Linie l3 = new Linie(p3, p4);
		Linie l4 = new Linie(p4, p5);
		Linie l5 = new Linie(p5b, p6);
		Linie l6 = new Linie(p6, p7);
		Linie l7 = new Linie(p7b, p8);
		Linie l8 = new Linie(p8, p9);
		Linie l9 = new Linie(p10, p11);
		Linie l10 = new Linie(p11, p12);
		Linie la = new Linie(pa1, pa2);
		List<Linie> linien = new ArrayList<Linie>();
		linien.add(l1);
		linien.add(l2);
		linien.add(l3);
		linien.add(l4);
		linien.add(l5);
		linien.add(l6);
		linien.add(l7);
		linien.add(l8);
		linien.add(l9);
		linien.add(l10);
		linien.add(la);
		this.linien = linien;
	}

}
