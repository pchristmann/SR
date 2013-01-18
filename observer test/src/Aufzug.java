import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Aufzug extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -863277498367621599L;
	
	/*
	 * Y-Koordinaten für die Etagen.
	 */
	public static final int KELLER = AufzugsSimulation.SCHACHTHOEHE/8*7;
	public static final int EG = AufzugsSimulation.SCHACHTHOEHE/8*6;
	public static final int ETAGE1 = AufzugsSimulation.SCHACHTHOEHE/8*5;
	public static final int ETAGE2 = AufzugsSimulation.SCHACHTHOEHE/8*4;
	public static final int ETAGE3 = AufzugsSimulation.SCHACHTHOEHE/8*3;
	public static final int ETAGE4 = AufzugsSimulation.SCHACHTHOEHE/8*2;
	public static final int ETAGE5 = AufzugsSimulation.SCHACHTHOEHE/8*1;
	public static final int ETAGE6 = AufzugsSimulation.SCHACHTHOEHE/8*0;
	
	/*
	 * Das Aufzugspanel hat für jede Etage einen Knopf;
	 */
	private JButton keller,eg,e1,e2,e3,e4,e5,e6;

	AufzugsSimulation gui; 
	Aufzugdaten daten;
	Thread worker;
	public int id ;
	public Aufzug(int id,AufzugsSimulation gui){
		this.id = id;
		this.gui = gui;
		
		
		/*
		 * Alle Aktionen des Aufzugs werden in einem eigenen Thread bearbeitet.
		 */
		
		this.daten = new Aufzugdaten(id,gui) ;
		worker = new Thread(daten);
	
		
		this.setSize(100, 100);
		this.setLayout(new FlowLayout());
		this.setBackground(Color.red);
		keller = new JButton("K");
		keller.setFont(new Font("Arial", Font.PLAIN, 8));
		eg = new JButton("eg");
		eg.setFont(new Font("Arial", Font.PLAIN,8));
		e1 = new JButton("e1");
		e1.setFont(new Font("Arial", Font.PLAIN, 8));
		e2 = new JButton("e2");
		e2.setFont(new Font("Arial", Font.PLAIN, 8));
		e3 = new JButton("e3");
		e3.setFont(new Font("Arial", Font.PLAIN, 8));
		e4 = new JButton("e4");
		e4.setFont(new Font("Arial", Font.PLAIN, 8));
		e5 = new JButton("e5");
		e5.setFont(new Font("Arial", Font.PLAIN, 8));
		e6 = new JButton("e6");
		e6.setFont(new Font("Arial", Font.PLAIN, 8));


		keller.addActionListener(this);
		eg.addActionListener(this);
		e1.addActionListener(this);
		e2.addActionListener(this);
		e3.addActionListener(this);
		e4.addActionListener(this);
		e5.addActionListener(this);
		e6.addActionListener(this);

		this.add(keller);
		this.add(eg);
		this.add(e1);
		this.add(e2);
		this.add(e3);
		this.add(e4);
		this.add(e5);
		this.add(e6);


		worker.start();


	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		switch(cmd){
		
		/*
		 * Wird ein Knopf gedrückt wird der Befehl an den Aufzugsthread
		 * weitergegeben.
		 */

		case "K": daten.push(KELLER);
		break;
		case "eg": daten.push(EG);
		break;
		case "e1": daten.push(ETAGE1);
		break;
		case "e2": daten.push(ETAGE2);
		break;
		case "e3": daten.push(ETAGE3);
		break;
		case "e4": daten.push(ETAGE4);
		break;
		case "e5": daten.push(ETAGE5);
		break;
		case "e6": daten.push(ETAGE6);
		break;

		}
	}
	
	
	public int getStatus(){
		/*
		 * Gibt an ob der Aufzug fährt, steht oder defekt ist.
		 */
		return daten.getStatus();
		
	}
	public int getFahrtrichtung(){
		/*
		 * Gibt die Fahrtrichtung des Aufzugs an.
		 */
		return daten.getFahrtrichtung();
	}
	
	public int getTuerStatus(){
		/*
		 * Gibt an ob die Tür auf oder zu ist.
		 */
		return daten.getTuerStatus();
	}
}
