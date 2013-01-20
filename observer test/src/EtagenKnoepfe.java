import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;


public class EtagenKnoepfe extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton keller,eg,e1,e2,e3,e4,e5,e6;
	private Vector<Aufzug> aufzuege;
	public EtagenKnoepfe(Aufzug aufzug){
		super();
		this.aufzuege = new Vector<>();
		aufzuege.add(aufzug);
		this.setLayout(new GridLayout(8,1));
		this.setSize(50, AufzugsSimulation.SCHACHTHOEHE);
		this.setBackground(Color.white);
		this.setPreferredSize(this.getSize());


		keller = new JButton("K");

		eg = new JButton("EG");

		e1 = new JButton("1");

		e2 = new JButton("2");

		e3 = new JButton("3");

		e4 = new JButton("4");

		e5 = new JButton("5");

		e6 = new JButton("6");



		keller.addActionListener(this);
		eg.addActionListener(this);
		e1.addActionListener(this);
		e2.addActionListener(this);
		e3.addActionListener(this);
		e4.addActionListener(this);
		e5.addActionListener(this);
		e6.addActionListener(this);

		this.add(e6);
		this.add(e5);
		this.add(e4);
		this.add(e3);
		this.add(e2);
		this.add(e1);
		this.add(eg);
		this.add(keller);
	}

	public void addAufzug(Aufzug aufzug){
		//Ein Aufzug wird hinzugefügt der mit den Knöpfen bedient .
		aufzuege.add(aufzug);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();

		/*
		 * Der jeweilige Aufzug wird benachrichtigt.
		 */
		switch(cmd){

		case "K": getNaechsterAufzug(Aufzug.KELLER);
		break;
		case "EG":  getNaechsterAufzug(Aufzug.EG);
		break;
		case "1":  getNaechsterAufzug(Aufzug.ETAGE1);
		break;
		case "2":  getNaechsterAufzug(Aufzug.ETAGE2);
		break;
		case "3": getNaechsterAufzug(Aufzug.ETAGE3);
		break;
		case "4": getNaechsterAufzug(Aufzug.ETAGE4);
		break;
		case "5":  getNaechsterAufzug(Aufzug.ETAGE5);
		break;
		case "6":  getNaechsterAufzug(Aufzug.ETAGE6);
		break;

		}
	}

	private void  getNaechsterAufzug(int etage){

		/*
		 * Der nächste Aufzug wird ermittelt.
		 */


		Aufzug aufzug = aufzuege.get(0);
		int min_distanz= AufzugsSimulation.SCHACHTHOEHE;
		Iterator<Aufzug> it= aufzuege.iterator();
		Aufzug tmp ;
		while(it.hasNext()){
			tmp = it.next();

			if(tmp.getStatus() != Aufzugdaten.DEFEKT){


				if(tmp.getStatus() == Aufzugdaten.STEHT){
					if(min_distanz > Math.abs(tmp.getAufzugdaten().getY() - etage)  ){
						min_distanz= Math.abs(tmp.getAufzugdaten().getY() - etage);
						aufzug = tmp;
					}
				}
				/*
				 * Liegt die Etage auf dem Weg.
				 */
				else if (tmp.getStatus() == Aufzugdaten.FAEHRT){
					if ((etage > tmp.getAufzugdaten().getY() && tmp.getFahrtrichtung() == Aufzugdaten.RUNTER )||
							(etage < tmp.getAufzugdaten().getY() && tmp.getFahrtrichtung() == Aufzugdaten.HOCH)){
						min_distanz = Math.abs(tmp.getAufzugdaten().getY() - etage);
						aufzug =tmp;

					}
				}
			}
		}
		aufzug.getAufzugdaten().push(etage);

	}


}
