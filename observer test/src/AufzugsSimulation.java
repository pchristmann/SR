import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;


public  class AufzugsSimulation extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1283115579719868238L;
	
	public static final int SCHACHTHOEHE = 700;
	
	private Aufzug a1,a2,a3 ;
	private final Statusanzeige aufzuegeStatus;
	private EtagenKnoepfe knoepfe1;


	
	public AufzugsSimulation(){
		super("Aufzuege");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		

		this.setLayout(new FlowLayout());
		
		/*
		 * Eine Menuleiste wird erstellt die zum deaktivieren und aktivieren der
		 * Aufzüge benutzt wird.
		 */
		
		JMenuBar menu= new JMenuBar();
		
		final JMenuItem defekt1 = new JMenuItem("Aufzug1 in Betrieb");
		defekt1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(a1.getStatus() != Aufzugdaten.DEFEKT){
					a1.setStatus(Aufzugdaten.DEFEKT);
					defekt1.setText("Aufzug1 Defekt");
					
				}
				else {
					a1.setStatus(Aufzugdaten.STEHT);
					defekt1.setText("Aufzug1 in Betrieb");
				}
			}
			
		});
		menu.add(defekt1);
		
		
		final JMenuItem defekt2 = new JMenuItem("Aufzug2 in Betrieb");
		defekt2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(a2.getStatus() != Aufzugdaten.DEFEKT){
					a2.setStatus(Aufzugdaten.DEFEKT);
					defekt2.setText("Aufzug2 Defekt");
					
				}
				else {
					a2.setStatus(Aufzugdaten.STEHT);
					defekt2.setText("Aufzug2 in Betrieb");
				}
			}
			
		});
		menu.add(defekt2);
		
		final JMenuItem defekt3 = new JMenuItem("Aufzug3 in Betrieb");
		defekt3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(a3.getStatus() != Aufzugdaten.DEFEKT){
					a3.setStatus(Aufzugdaten.DEFEKT);
					defekt3.setText("Aufzug3 Defekt");
					
				}
				else {
					a3.setStatus(Aufzugdaten.STEHT);
					defekt3.setText("Aufzug3 in Betrieb");
				}
			}
			
		});
		menu.add(defekt3);
		
		this.setJMenuBar(menu);

		

		/*
		 * Es werden drei Aufzugsschächte erstellt;
		 */
		JPanel aufzugschacht1 = new JPanel();
		aufzugschacht1.setLayout(null);
		aufzugschacht1.setBackground(Color.DARK_GRAY);
		aufzugschacht1.setPreferredSize(new Dimension(100,SCHACHTHOEHE));

		JPanel aufzugschacht2 = new JPanel();
		aufzugschacht2.setLayout(null);
		aufzugschacht2.setBackground(Color.DARK_GRAY);
		aufzugschacht2.setPreferredSize(new Dimension(100,SCHACHTHOEHE));

		JPanel aufzugschacht3 = new JPanel();
		aufzugschacht3.setLayout(null);
		aufzugschacht3.setBackground(Color.DARK_GRAY);
		aufzugschacht3.setPreferredSize(new Dimension(100,SCHACHTHOEHE));
		
		this.setResizable(false);
		
		/*
		 * Ein neuer Frame wird erstellt in dem der Status der Aufzüge
		 * angezeigt wird.
		 */
		aufzuegeStatus = new Statusanzeige();
		this.addComponentListener(new ComponentAdapter() {
			
			
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				/*
				 * Status anzeige an den Frame anheften 
				 */
				aufzuegeStatus.setLocation(getLocation().x+getWidth(),getY());
				aufzuegeStatus.setVisible(true);
			}
			
			
		});
		

		/*
		 * Drei Aufzüge werden erstell.
		 * Jeder wird einem Aufzugschacht zugeordnet und 
		 * bei der Statusanzeige angemeldet.
		 */
		
		a1 = new Aufzug(1,this);
		aufzugschacht1.add(a1);
		aufzuegeStatus.addAufzugsanzeige(a1);

		a2 = new Aufzug(2,this);
		aufzugschacht2.add(a2);
		aufzuegeStatus.addAufzugsanzeige(a2);


		a3 = new Aufzug(3,this);
		aufzugschacht3.add(a3);
		aufzuegeStatus.addAufzugsanzeige(a3);

		/*
		 * Erzeugt pro Etage einen Knopf 
		 * Jeder Aufzug wird angemeldet.
		 */
		knoepfe1 = new EtagenKnoepfe(a1);
		knoepfe1.addAufzug(a2);
		knoepfe1.addAufzug(a3);
		this.add(knoepfe1);
		
		
		this.add(aufzugschacht1);

		this.add(aufzugschacht2);

		this.add(aufzugschacht3);
		pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);


		/*
		 * Status anzeige an den Frame anheften 
		 */
		this.aufzuegeStatus.setLocation(this.getWidth(),this.getY());
		this.aufzuegeStatus.setVisible(true);
	}
	@Override
	public void update(Observable o, Object arg) {
		/*
		 * Jeder Aufzusthread löst update aus, das dei Position des jeweiligen Aufzuges
		 * aktualisiert.
		 */
		if(arg instanceof Point){
			if (((Aufzugdaten)o).getId()==1){
				
				a1.setLocation((Point) arg);
			}
			if (((Aufzugdaten)o).getId()==2){
				a2.setLocation((Point) arg);
			}
			if (((Aufzugdaten)o).getId()==3){
				a3.setLocation((Point) arg);
			}
		}	
		
	}
	
	public Statusanzeige getAufzuegeStatus() {
		return aufzuegeStatus;
	}

	public static void main(String[] args){
		new AufzugsSimulation();
	}


}
