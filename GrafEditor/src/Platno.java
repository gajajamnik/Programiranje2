import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Platno extends JPanel {
	
	protected Graf graf;
	protected Color barvaPovezave;
	protected Color barvaTocke;
	protected Color barvaRoba;
	protected float debelinaPovezave;
	protected float debelinaRoba;
	protected int polmer;
	
	public Platno(int sirina, int visina) {
		super(); //poklices konstruktor nadrazreda
		setPreferredSize(new Dimension(sirina, visina));
		graf = null;
		this.barvaPovezave = Color.GRAY;
		this.barvaTocke = Color.MAGENTA;
		this.barvaRoba = Color.CYAN;
		this.debelinaPovezave = 3;
		this.debelinaRoba = 4;
		this.polmer = 20;
		
	}
	
	public void nastaviGraf(Graf graf) {
		this.graf = graf;
		repaint(); // z to metodo zahtevas da se slika obnovi
	}
	
	private static int round(double x) {
		return (int)(x + 0.5); 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //poklices metodo iz nadrazreda
		Graphics2D g2D = (Graphics2D) g;
		
		if (graf == null) return;
		
		g2D.setColor(barvaPovezave);
		g2D.setStroke(new BasicStroke(this.debelinaPovezave));
		
		for (Tocka tocka : graf.tocke.values()) {
			for (Tocka sosed : tocka.sosedi) {
				if (tocka.ime.compareTo(sosed.ime) < 0) { //pogoj da vsako crto samo enx narisemo
					g.drawLine(round(tocka.x), round(tocka.y), round(sosed.x), round(sosed.y));
				}
			}
		}
		
		// za risanje crt navaden g za risanje ovalov rabimo Graphics torej 2D
		
		for (Tocka tocka : graf.tocke.values()) {
			g2D.setColor(barvaRoba);
			g2D.fillOval(round(tocka.x - polmer - debelinaRoba), round(tocka.y - polmer - debelinaRoba), round(polmer + debelinaRoba) * 2, round(polmer + debelinaRoba) * 2);
			g2D.setColor(barvaTocke);
			g2D.fillOval(round(tocka.x - polmer), round(tocka.y - polmer), polmer * 2, polmer * 2); //notranjost
		}
	}

}

