package passwordGame.grafica;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import passwordGame.model.EsitoTentativo;
import passwordGame.model.GiocoPassword;
import passwordGame.model.StatoLettera;

public class View {
	
	// Elementi dell'interfaccia
	private Label[][] labels;
	private Label[] linputs;
	private GridPane gridPane = new GridPane();
	private Button btnTry=new Button("Invia");
	private Button btnInfo=new Button("?");
	private Button btnHelp=new Button("Help");
	private HBox hb = new HBox(btnInfo, btnTry ,btnHelp);
	private VBox vb = new VBox(gridPane,hb);
	private Scene scene = new Scene(vb);
	
	// Attributi
	private int maxTry;
	private int maxWord;
	private int tryPos=0;
	private boolean loose=false;
	private GiocoPassword model;

	// Costanti
	private final Color colorCorretta=Color.DARKORANGE;
	private final Color colorPresente=Color.ROYALBLUE;
	private final Color colorAssente=Color.GREY;
	
	// Costruttore
	public View(Controller c, GiocoPassword m) {	 
		model=m;
	    maxTry=m.getTentativiMassimi();  
	    maxWord=m.getLunghezzaParola();
	    
	    // Costruisce la tabella
	    tableBuild(gridPane,maxWord);
	    
	    // Stili, spaziature e dimensioni
	    gridPane.setHgap(2);
	    
	    hb.setPadding(new Insets(40, 10, 10, 10)); 
	    hb.setSpacing(40);
	    hb.setAlignment(Pos.CENTER); 
	    
	    btnTry.getStyleClass().add("trybutton");
	    btnInfo.setMaxSize(60, maxTry);
	    btnHelp.setMaxSize(60, maxTry);
	    
	    scene.getStylesheets().addAll(getClass().getResource("/styles.css").toExternalForm()); 
	    
	    // Gestori degli eventi
	    setHandlers(c);
	}

	// Restituisce la scena	
	public Scene getScene() {
		return(scene);
	}
	
	// Scrive una stringa in una delle matrici di gioco
	private void lwrite(String text, List<StatoLettera> stati, int line) {
		Label[] lbls=labels[line];
		for (int i=0;i<lbls.length && i<text.length();i++) {
			lbls[i].setText(""+text.toUpperCase().charAt(i));
			switch (stati.get(i) ) {
				case CORRETTA:
					setColor(line, i, colorCorretta);
					break;
				case PRESENTE:
					setColor(line, i, colorPresente);
					break;
				case ASSENTE:
					setColor(line, i, colorAssente);
					break;
			}
		}
	}
	
	private void setColor(int posY, int posX, Color cl) {
		labels[posY][posX].setTextFill(Color.WHITE);
		labels[posY][posX].setBackground(new Background(new BackgroundFill(cl, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	// Costruisce la matrice delle labels e il vettore di input
	
	private void tableBuild(GridPane gp, int panesize ) {
		//Setta dimensione del pane  
	    gridPane.setMinSize(200, 200); 
	     
	    //Setta il padding  
	    gridPane.setPadding(new Insets(5, 15, 15, 15)); 
	    
	    //Setta la distanza verticale e orizontale tra le colonne 
	    gridPane.setVgap(2); 
	    gridPane.setHgap(2);       
	    
	    //Setta l'allineamento della Grid 
	    gridPane.setAlignment(Pos.TOP_LEFT);
		labels = new Label[maxTry][panesize];
		for (int i=0;i<labels.length;i++) {
			for (int j=0;j<labels[i].length;j++) {
				labels[i][j]=new Label(" ");
				labels[i][j].getStyleClass().add("label-bordata");
				gp.add(labels[i][j], j,i );
			}
		}
		linputs = new Label[panesize];
	    for (int i=0;i<linputs.length;i++) {
	    	  linputs[i]=new Label(" ");
	    	  linputs[i].getStyleClass().add("label-linput");
	    	  gp.add(linputs[i], i,maxTry+4);
	    }
	}
		
	// Gestione degli eventi
	private void setHandlers(Controller c) {    
		btnTry.setDisable(true);
	    // Registra una copia del componente attivo nel controller
	    c.registerComponent(linputs, this);
	    
	    // tasto premuto
	    scene.setOnKeyPressed(e -> {
			btnTry.setDisable(c.manageKey(e.getCode()));
			btnTry.requestFocus();
			btnTry.setDefaultButton(true);
		});
	    // bottone
	    btnTry.setOnAction(e -> {
	    	if (tryPos<maxTry) {
	    		EsitoTentativo esito=c.manageTryButton();
	    		if (esito.isTentativoValido()) {
		    		lwrite(esito.getParolaInserita(), esito.getStati(), tryPos++);
		    		btnTry.setDisable(true);
		    		if (esito.isParolaIndovinata()) {
		    			Dialogs.winDialog();
		    			Platform.exit();
		    		} 
	    		} else {
	    			Dialogs.notValidDialog();
	    		}
	    	} 
	    	if (tryPos==maxTry) {
	    		scene.setOnKeyPressed(null);
	    		Dialogs.looseDialog(model.getParolaSegreta());
	    		Platform.exit();
	    	}
	    });
	    btnInfo.setOnAction(e -> {
	    	c.mangeInfoButton();
	    });
	}
}
