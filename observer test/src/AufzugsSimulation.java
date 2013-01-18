import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class AufzugsSimulation extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1283115579719868238L;
	
	public static final int SCHACHTHOEHE = 700;
	
	Aufzug a1,a2,a3 ;
	Statusanzeige aufzuegeStatus;



	public AufzugsSimulation(){

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		this.setTitle("Aufzuege");
		//this.setSize(500, 700);

		this.setLayout(new FlowLayout());
		


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
		
		/*
		 * Ein neuer Frame wird erstellt in dem der Status der Aufzüge
		 * angezeigt wird.
		 */
		aufzuegeStatus = new Statusanzeige();

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
		 * Erzeugt pro Etage einen Knopf und jeder Aufzug wird angemeldet.
		 */
		EtagenKnoepfe knoepfe1 = new EtagenKnoepfe(a1);
		knoepfe1.addAufzug(a2);
		knoepfe1.addAufzug(a3);
		this.add(knoepfe1);
		
		
		this.add(aufzugschacht1);

		this.add(aufzugschacht2);

		this.add(aufzugschacht3);
		pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void update(Observable o, Object arg) {
		/*
		 * Jeder Aufzusthread löst update aus, das dei Position des jeweiligen Aufzuges
		 * aktualisiert.
		 */
		if(arg instanceof Point){
			if (((Aufzugdaten)o).id==1){
				
				a1.setLocation((Point) arg);
			}
			if (((Aufzugdaten)o).id==2){
				a2.setLocation((Point) arg);
			}
			if (((Aufzugdaten)o).id==3){
				a3.setLocation((Point) arg);
			}
		}	
		
	}

	public static void main(String[] args){
		new AufzugsSimulation();
	}


}
