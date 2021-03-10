import javax.swing.JFrame;

public class Okno extends JFrame{
	
	protected Platno platno;
	
	public Okno() {
		super();
		setTitle("Urejevalnik grafov");
		platno = new Platno(800, 800);
		add(platno);
		
	}
	

}
