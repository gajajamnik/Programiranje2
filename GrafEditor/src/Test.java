
public class Test {

	public static void main(String[] args) {
		Graf g = Graf.poln(7);
		g.razporedi(400, 400, 300);
		g.izpis();
		
		Okno okno = new Okno();
		okno.pack();
		okno.setVisible(true);
		okno.platno.nastaviGraf(g);

	}

}
