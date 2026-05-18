package passwordGame.grafica;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import passwordGame.Main;
import passwordGame.model.EsitoTentativo;
import passwordGame.model.GiocoPassword;


public class Controller {
	private Label[] ls;
	private int inpos=0; // posione dell'input
	private GiocoPassword model;
	private Stage primaryStage;
	
    // Costruttore 	
	public Controller(GiocoPassword model, Stage primaryStage) {
		this.model=model;
		this.primaryStage=primaryStage;
	}

	// Registra l'array di input che deve essere noto al controller
	public void registerComponent(Label[] ls, View v) {
        this.ls=ls;

	}

	// Gestisce la pressione di un tasto
	public boolean manageKey(KeyCode kc) {
		Main.dprint("Hai premuto "+kc.getChar()+" "+kc);
		if (kc==KeyCode.BACK_SPACE) {
			if (inpos>0)inpos--;
			ls[inpos].setText("");
		} 
		else if( kc.isLetterKey() ) {
			if (inpos<ls.length) {
				ls[inpos++].setText(kc.getChar());
			}
		}
		return(inpos!=ls.length);
	}
	
	// Gestisce il bottone "prova"
	public EsitoTentativo manageTryButton() {
		String testo="";
		for (Label l : ls) {
			testo+=l.getText();
		}
		EsitoTentativo e =model.giocaTentativo(testo); 
		if (e.isTentativoValido()) {
		for (Label l : ls) {	
				l.setText("");
				inpos=0;
			}
		}
		Main.dprint("TESTO: "+testo);
		return e;
	}

	public void mangeInfoButton() {
		Dialogs.helpWindow(primaryStage);
	}	
}
