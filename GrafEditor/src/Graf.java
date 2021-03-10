import java.util.HashMap;
import java.util.Map;

public class Graf {
	
	private int stevec; //komponenta
	protected Map<String, Tocka> tocke;
	
	public Graf() {
		stevec = 0;
		tocke = new HashMap<String, Tocka>();
		
	}
	
	public Tocka tocka(String ime) {
		return tocke.get(ime); //get vrne null ce tega ni v slovarju
	}
	
	public boolean povezava(Tocka v, Tocka u) {
		return v.sosedi.contains(u);
	}
	
	//      |  to je tip rezultata metode
	public Tocka dodajTocko(String ime) {
		Tocka v = tocka(ime);
		if (v == null) {
			v = new Tocka(ime);
			tocke.put(ime, v); //dodajanje v slovar
		}
		return v; 
	}
	
	public Tocka dodajTocko() {
		while (true) {
			// ime tocke bomo vzeli iz stevca (stejem od 1 dalje)
			// tu ni vseeno kam napises ++ (ce das ++ na koncu bo stevec na zacetku 0, sele nato se poveca)
			String ime = Integer.toString(++stevec);
			// prevrit moramo ali tocka s takim imenom ze obstaja
			if (tocka(ime) != null) continue;
			Tocka v = new Tocka(ime);
			tocke.put(ime,  v);
			return v;	
		}
	}
	
	// void ker metoda ne vraca nicesar
	public void dodajPovezavo(Tocka v, Tocka u) {
		if (v == u) return;
		v.sosedi.add(u);
		u.sosedi.add(v);
		// ni problem ce sta tocki ze povezani saj so to mnozice (ni ponovitev)
	}
	
	public void odstraniPovezavo(Tocka v, Tocka u) {
		v.sosedi.remove(u);
		u.sosedi.remove(v);
	}
	
	public void odstraniTocko(Tocka v) {
		// najprej odstranimo vse povezave s to tocko
		
		// NAROBE: pri zanki vplivamo na mnozico po kateri teèe
		// for (Tocka u : v.sosedi) odstraniPovezavo(v, u);
		
		for (Tocka u : v.sosedi) u.sosedi.remove(v);
		tocke.remove(v.ime); //odstranjujemo po kljuèih(imenih)
	}
	
	private Tocka[] dodajTocke(int n) {
		Tocka[] tabela = new Tocka[n];
		for (int i = 0; i < n; ++i) tabela[i] = dodajTocko(); // dodajTocko vraca tocke in hkrati izpisuje v graf
		return tabela;
	}
	
	public static Graf prazen(int n) {
		Graf f = new Graf();
		f.dodajTocke(n);
		return f;
	}
	
	// staticne metode uporabljamo na objektih Graf.poln??
	// objektne metode mormo uporabljat na objektih torej najprej ustvarimo objekt Graf graf = new Graf() sele nato uporabis metodo
	
	public static Graf cikel(int n) {
		Graf f = new Graf();
		Tocka[] tabela = f.dodajTocke(n);
		for (int i = 0; i < n; ++i) {
			f.dodajPovezavo(tabela[i], tabela[(i + 1) % n]);
			//                                  ^ zato da ko pridemo do konca cikla gre na 0
		}
		return f;
	}
	
	public static Graf poln(int n) {
		Graf f = new Graf();
		Tocka[] tabela = f.dodajTocke(n);
		for (int i = 0; i < n; ++i) {
			for (int j = i + 1; j < n; j++) {
				f.dodajPovezavo(tabela[i], tabela[j]);
			}
		}
		return f;
	}
	
	public static Graf polnDvodelen(int n, int m) {
		Graf f = new Graf();
		Tocka[] tab1 = f.dodajTocke(n);
		Tocka[] tab2 = f.dodajTocke(m);
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; j++) {
				f.dodajPovezavo(tab1[i], tab2[j]);
			}
		}
		return f;
	}
	
	public void izpis() {
		for (Tocka v : tocke.values()) {
			System.out.print(v + ":");
			for (Tocka u : v.sosedi) System.out.print(" " + u);
			System.out.println();
		}
	}
	
	public void razporedi(double x, double y, double r) {
		int n = tocke.size(); // stevilo tock v slovarju
		int i = 0;
		for (Tocka v : tocke.values()) {
			v.x = x + r * Math.cos(2 * i * Math.PI / n);
			v.y = x + r * Math.sin(2 * i * Math.PI / n);
			++i;
		}
	}
	

}
