import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	protected Graf graf;
	protected Tocka aktivnaTocka;
	protected Set<Tocka> izbraneTocke;
	
	protected Color barvaPovezave;
	protected Color barvaTocke;
	protected Color barvaAktivneTocke;
	protected Color barvaIzbraneTocke;
	protected Color barvaRoba;
	protected float debelinaPovezave;
	protected float debelinaRoba;
	protected int polmer;
	
	public Platno(int sirina, int visina) {
		super(); //poklices konstruktor nadrazreda
		setPreferredSize(new Dimension(sirina, visina));
		graf = null;
		aktivnaTocka = null;
		izbraneTocke = new HashSet<Tocka>();
		
		this.barvaPovezave = Color.GRAY;
		this.barvaTocke = Color.BLACK;
		this.barvaAktivneTocke = Color.MAGENTA;
		this.barvaIzbraneTocke = Color.YELLOW;
		this.barvaRoba = Color.CYAN;
		this.debelinaPovezave = 3;
		this.debelinaRoba = 4;
		this.polmer = 20;
		
		addMouseListener(this); //sele s tem poves, da se nekaj zgodi ko nardis nekaj z misko
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true); //nastavis da je platno sposobno prejet focus in reagira na tipkovnico
	}
	
	public void nastaviGraf(Graf graf) {
		this.graf = graf;
		aktivnaTocka = null;
		izbraneTocke.clear();
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
		
		//risanje povezav
		for (Tocka tocka : graf.tocke.values()) {
			for (Tocka sosed : tocka.sosedi) {
				if (tocka.ime.compareTo(sosed.ime) < 0) { //pogoj da vsako crto samo enx narisemo
					g.drawLine(round(tocka.x), round(tocka.y), round(sosed.x), round(sosed.y));
				}
			}
		}
		
		// za risanje crt navaden g za risanje ovalov rabimo Graphics torej 2D
		
		//risanje tock
		for (Tocka tocka : graf.tocke.values()) {
			g2D.setColor(barvaRoba);
			g2D.fillOval(round(tocka.x - polmer - debelinaRoba), round(tocka.y - polmer - debelinaRoba), round(polmer + debelinaRoba) * 2, round(polmer + debelinaRoba) * 2);
			if (tocka == aktivnaTocka) g.setColor(barvaAktivneTocke);
			else if (izbraneTocke.contains(tocka)) g.setColor(barvaIzbraneTocke);
			else g.setColor(barvaTocke);
			g2D.fillOval(round(tocka.x - polmer), round(tocka.y - polmer), polmer * 2, polmer * 2); //notranjost
		}
	}
	
	private int stariX, stariY;
	private int klikX, klikY;
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (graf == null) return;
		int x = e.getX();
		int y = e.getY();
		klikX = x;
		klikY = y;
		stariX = x;
		stariY = y;
		Tocka izbranaTocka = null;
		double najmanjsaRazdalja = 0;
		for (Tocka u : graf.tocke.values()) {
			if ((u.x - x)*(u.x - x) + (u.y - y)*(u.y - y) < polmer*polmer) aktivnaTocka = u;
		}
		repaint();
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (aktivnaTocka != null) {
			int razlikaX = x - stariX;
			int razlikaY = y - stariY;
			aktivnaTocka.x += razlikaX;
			aktivnaTocka.y += razlikaY;
		}
		else {
			for (Tocka u : izbraneTocke) {
				int razlikaX = x - stariX;
				int razlikaY = y - stariY;
				u.x += razlikaX;
				u.y += razlikaY;
			}
		}
		stariX = x;
		stariY = y;
		repaint();
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//se ne premakne
		if (klikX == x && klikY == y) {
			if (aktivnaTocka != null) {
				if (izbraneTocke.contains(aktivnaTocka)) izbraneTocke.remove(aktivnaTocka);
				else izbraneTocke.add(aktivnaTocka);
			}
			else {
				Tocka v = graf.dodajTocko();
				v.x = x;
				v.y = y;
				for (Tocka u : izbraneTocke) {
					graf.dodajPovezavo(v, u);
				}
			}
		
		}
		aktivnaTocka = null;
		repaint();
		
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char tipka = e.getKeyChar();
		if (tipka == 'a') {
			izbraneTocke.addAll(graf.tocke.values());
		}
		else if (tipka == 's') {
			izbraneTocke.clear();
		}
		else if (tipka == 't') {
			for (Tocka v : izbraneTocke) graf.odstraniTocko(v);
			izbraneTocke.clear();
		}
		// manjka
		
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}

