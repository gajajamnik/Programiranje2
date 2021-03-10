import java.util.HashSet;
import java.util.Set;

public class Tocka {
	
	protected String ime;
	protected Set<Tocka> sosedi;
	protected double x, y;
	
	// ustvarimo konstruktor - ISTO IME KOT RAZRED, NIMA TIPA REZULTATA
	public Tocka(String ime) {
		// v pythonu: self.ime = ime -> namesto self this
		this.ime = ime;
		// tu ni treba napisat this(lahko bi) ker ni drugih sosedov ki so dosegljivi
		// primer: ime = ime2 je vredu ker ve da se ime nanasa na konstr
		sosedi = new HashSet<Tocka>(); //poklièemo konstruktor množice toèk
		x = y = 0;
	}
	
	// ustvarimo metodo
	public int stopnja() {
		return sosedi.size(); //velikost množice
	}
	
	// s to metodo povozimo definicijo iz nadrazreda zato rabimo Override
	@Override
	public String toString() {
		return ime;
	}
	

}
