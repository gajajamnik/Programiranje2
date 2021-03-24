import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Okno extends JFrame implements ActionListener{
	
	protected Platno platno;
	
	private JMenuItem menuOdpri, menuShrani, menuKoncaj;
	private JMenuItem menuPrazen, menuCikel, menuPoln, menuPolnDvodelen;
	private JMenuItem menuBarvaPovezave, menuBarvaTocke, menuBarvaAktivneTocke, menuBarvaIzbraneTocke;
	private JMenuItem menuBarvaRoba, menuDebelinaRoba, menuDebelinaPovezave;
	
	public Okno() {
		super();
		setTitle("Urejevalnik grafov");
		platno = new Platno(800, 800);
		add(platno);
		
		// nastavitev ogrodja menija
		JMenuBar menubar = new JMenuBar();
		
		JMenu menuDatoteka = dodajMenu(menubar, "Datoteka");
		JMenu menuGraf = dodajMenu(menubar, "Graf");
		JMenu menuNastavitve = dodajMenu(menubar, "Nastavitve");
		
		setJMenuBar(menubar); // da ga res dodas v okno
		
		// dodajanje izbir v meni tipa JMenuItem
		menuOdpri = dodajMenuItem(menuDatoteka, "Odpri ...");
		menuShrani = dodajMenuItem(menuDatoteka, "Odpri ...");
		menuKoncaj = dodajMenuItem(menuDatoteka, "Odpri ...");
		menuPrazen = dodajMenuItem(menuGraf, "Odpri ...");
		menuCikel = dodajMenuItem(menuGraf, "Odpri ...");
		menuPoln = dodajMenuItem(menuGraf, "Odpri ...");
		menuPolnDvodelen = dodajMenuItem(menuGraf, "Odpri ...");
		menuBarvaPovezave = dodajMenuItem(menuNastavitve, "Odpri ...");
		menuBarvaTocke = dodajMenuItem(menuNastavitve, "Odpri ...");
		menuBarvaAktivneTocke = dodajMenuItem(menuNastavitve, "Odpri ...");
		menuBarvaIzbraneTocke = dodajMenuItem(menuNastavitve, "Odpri ...");
		menuBarvaRoba = dodajMenuItem(menuNastavitve, "Odpri ...");
		menuDebelinaRoba = dodajMenuItem(menuNastavitve, "Odpri ...");
		menuDebelinaPovezave = dodajMenuItem(menuNastavitve, "Odpri ...");
	}
	
	// da ne ponavljas ves cas iste kode definiras to metodo
	public JMenu dodajMenu(JMenuBar menubar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menubar.add(menu);
		return menu;
	}
	
	public JMenuItem dodajMenuItem(JMenu menu, String naslov) {
		JMenuItem menuItem = new JMenuItem(naslov);
		menu.add(menuItem);
		menuItem.addActionListener(this);
		return menuItem;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource(); 
		if (source == menuOdpri) { //menuOdpri ne najde ker je definiran znotraj konstruktorja, zato moras na vrhu definirat kot privatno komponento
			JFileChooser dialog = new JFileChooser();
			int izbira = dialog.showOpenDialog(this); //s tem res okno prikaže
			// to je celo št ki doloèa kaj je uporabnik kliknu
			// ce je izbral pritrdilno (vrjetno open file)
			if (izbira == JFileChooser.APPROVE_OPTION) {
				String ime = dialog.getSelectedFile().getPath();
				// preberimo graf iz datoteke
				
				//Graf g = Graf.preberi(ime);
				//platno.nastaviGraf(g);
			}
		}
		else if (source == menuShrani) {
			JFileChooser dialog = new JFileChooser();
			int izbira = dialog.showSaveDialog(this);
			if (izbira == JFileChooser.APPROVE_OPTION) {
				String ime = dialog.getSelectedFile().getPath();
				//platno.graf.shrani(ime);
			}
		}
		else if (source == menuKoncaj) {
			// s tem zakljucimo program
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}

		else if (source == menuPrazen) {
			String steviloTock = JOptionPane.showInputDialog(this, "Število toèk:");
			// ce uporabnik klikne krizec je ta str enak null
			if (steviloTock != null && steviloTock.matches("\\d+")) {
				Graf g = Graf.prazen(Integer.parseInt(steviloTock));
				g.razporedi(400, 400, 300);
				platno.nastaviGraf(g);
			}
		}
		else if (source == menuCikel) {
			
		}
		else if (source == menuPoln) {
			
		}
		else if (source == menuPolnDvodelen) {
			JTextField nn = new JTextField();
			JTextField mm = new JTextField();
			// tokrat sami nastavimo polja za vpis
			JComponent[] polja = {
					new JLabel("Vnesi N: "), nn, 
					new JLabel("Vnesi M"), mm,
			};
			// odpre se okno z zgoraj definiranimi polju z dodatkom Ok in CANCEL
			int izbira = JOptionPane.showConfirmDialog(this, polja, "Input", JOptionPane.OK_CANCEL_OPTION);
			if (izbira == JOptionPane.OK_OPTION && nn.getText().matches("\\d+") && nn.getText().matches("\\d+")) {
				Graf g = Graf.polnDvodelen(Integer.parseInt(nn.getText()), Integer.parseInt(mm.getText()));
				g.razporedi(400, 400, 300);
				platno.nastaviGraf(g);
			}
		}
		else if (source == menuBarvaPovezave) {
			Color barva = JColorChooser.showDialog(this, "Izberi barvo povezav", platno.barvaPovezave);
			if (barva != null) {
				platno.barvaPovezave = barva;
				platno.repaint();
			}
		}
		else if (source == menuBarvaTocke) {
			
		}
		else if (source == menuBarvaAktivneTocke) {
			
		}
		else if (source == menuBarvaIzbraneTocke) {
			
		}
		else if (source == menuDebelinaRoba) {
			
		}
		else if (source == menuDebelinaPovezave) {
			
		}


	}
	
	

}
