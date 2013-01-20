
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Statusanzeige extends JFrame implements Observer {


	private HashMap<Integer,JLabel>  labels ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Statusanzeige(){
		
		super("Statusanzeige");
		labels= new HashMap<>();
		
		
		 
		
		this.setLayout(new GridLayout(3,0));
		this.setResizable(false);
		//this.setVisible(true);
	}

	public void addAufzugsanzeige(Aufzug a){

		/*
		 * Eine neus Label wird erzeugt in dem der Status eines Aufzuges 
		 * angezeigt wird.
		 */
		if(!labels.containsKey(a.id)){
			JLabel tmp = new JLabel(a.getAufzugdaten().toString());

			this.add(tmp);
			labels.put(a.id,tmp);

			pack();
		}
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		/*
		 * Das von den Aufzugsdaten ausgelöste Update sucht das richtige Label 
		 * aus der HashMap und ändert den Status.
		 * Die Oberflächte passt sich der Lablegröße an.
		 */

		if(arg instanceof Aufzugdaten ){
			int id = ((Aufzugdaten)o).getId();
			if(labels.containsKey(id)){
				labels.get(id).setText(((Aufzugdaten)arg).toString());
				pack();
			}
		}

	}
	
	


}
