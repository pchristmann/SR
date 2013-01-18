import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;


public class Aufzugdaten extends Observable implements Runnable {
	
	//Fahrtrichtung
	public static final int HOCH = 1;
	public static final int RUNTER = 2;

	//Türstatus
	public static final int TUER_OFFEN =3;
	public static final int TUER_GESCHLOSSEN = 4;

	//Aufzugsstatus
	public static final int FAEHRT= 5;
	public static final int DEFEKT = 6;
	public  static final int STEHT = 7;

	int x,y,id;
	int etage ;
	int fahrtrichtung,status,tuer;
	Vector<Integer> warteschlange;


	public Aufzugdaten(int id,Observer e) {
		/*
		 * Die AufzugsSimulation und die Status anzeige wird als Observer hinzugefügt. 
		 */
		this.id = id;
		this.addObserver(e);
		this.addObserver( ((AufzugsSimulation)e).aufzuegeStatus);
		x = 0;
		y = 0;
		warteschlange = new Vector<>();
		etage = Aufzug.ETAGE6;
		status = STEHT;
		tuer = TUER_GESCHLOSSEN;
	}

	public void call( int y){
		
		/*
		 * Der AufzugsSimulation wird die aktuelle Position mitgeteilt.
		 */
		
		this.y = y;
		setChanged();
		notifyObservers(new Point(this.x,y));
	}
	
	private void pushStatusUpdate(){
		/*
		 * Die Statusanzeige wird aktualisiert.
		 */
		setChanged();
		notifyObservers(this);
	}
	public String toString(){
		
		
		
		String status = "Aufzug "+id+": ";
		
		switch(this.getStatus()){
			case FAEHRT: 
				status += "Status: Fahert ";
				break;
			case STEHT: 
				status += "Status: Steht ";
				break;
			case DEFEKT: 
				status += "Status: Defekt ";
				break;
		}
		
		if(this.getFahrtrichtung() == HOCH){
			status += "Fahrtrichtung: Hoch ";
		}else {
			status += "Fahrtrichtung: Runter ";
		}
		
		if(this.getTuerStatus()== TUER_GESCHLOSSEN){
			status += "Tuer: Geschlossen";
		}else status += "Tuer: Offen";
		
		switch(etage){
		case Aufzug.ETAGE1: status += " Etage: 1";
			break;
		case Aufzug.ETAGE2: status += " Etage: 2";
		break;
		case Aufzug.ETAGE3: status += " Etage: 3";
		break;
		case Aufzug.ETAGE4: status += " Etage: 4";
		break;
		case Aufzug.ETAGE5: status += " Etage: 5";
		break;
		case Aufzug.ETAGE6: status += " Etage: 6";
		break;
		case Aufzug.EG: status += " Etage: EG";
		break;
		case Aufzug.KELLER: status += " Etage: Keller";
		break;
		
		}
		
		return status;
	}

	public void moveToEtage(int etage ){
		/*
		 * Die Aktuelle Position wird solang aktualisiet bis er die Etage erreicht hat.
		 */
		tuer = TUER_GESCHLOSSEN;
		status = FAEHRT;
		pushStatusUpdate();
		try {
			int i = this.y;
			System.out.println("move to: "+ etage +" von Pos: "+ i);
			if (etage <i){
				while(etage<= i){
					fahrtrichtung = HOCH;
					pushStatusUpdate();
					call(i);
					Thread.sleep(15);
					i--;
					// Wird ein Etagenknopf gedrückt der auf dem Weg zum
					// aktuellen Ziel liegt wird dort gehalten.
					if(!warteschlange.isEmpty()){
						if (warteschlange.get(0) > etage && warteschlange.get(0) <i){
							i = pop();
							moveToEtage(i);
						}
					}
				}
			}else{
				while(etage>=i){
					fahrtrichtung = RUNTER;
					pushStatusUpdate();
					call(i);
					Thread.sleep(15);
					i++;
					if(!warteschlange.isEmpty()){

						if (warteschlange.get(0) < etage && warteschlange.get(0) >i){
							System.out.println(warteschlange);
							i = pop();

							moveToEtage(i);

						}
					}
				}

			}
			this.etage = etage;
			tuer = TUER_OFFEN;
			status = STEHT;
			pushStatusUpdate();

			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void push(int etage){
		/*
		 * Eine Fahrtziel wird zur Warteschlange hinzugefügt.
		 */
		warteschlange.add(etage);
		notify();
	}

	public synchronized int pop(){
		
		/*
		 * Der Thread wartet bis ein neues Fahrtziehl hinzugefügt wird.
		 */
		while(warteschlange.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return  warteschlange.remove(0);
	}



	@Override
	public void run() {
		/*
		 *  Der Thread holt die Befehle aus der Warteschlange und 
		 *  arbeitet sie ab.
		 */
		
		while(true){
			
			int x = pop();
			moveToEtage(x);


		}

	}

	public int getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	public int getFahrtrichtung() {
		// TODO Auto-generated method stub
		return fahrtrichtung;
	}

	public int getTuerStatus(){
		return tuer;
	}



}
