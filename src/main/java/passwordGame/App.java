package passwordGame;
import javafx.application.Application;
import javafx.stage.Stage;
import passwordGame.grafica.Controller;
import passwordGame.grafica.Dialogs;
import passwordGame.grafica.View;
import passwordGame.model.GiocoPassword;


public class App extends Application {
	
	private final int BASEWORD = 4; // limite minimo di lettere
	
	@Override
	public void start(Stage stage) {      
	  // Dialog che seleziona il livello	
	  int lev=Dialogs.dialogLivello(stage); 
	  if (lev!=-1) {
		  int maxWord=BASEWORD+lev;   // Dimensione massima della parola 
	 	  int maxTry=maxWord+1;		  // Numero massimo di tentativi
		  // PROGETTO MVC
	 	  	  // Crea il model
		 	  GiocoPassword model= new GiocoPassword();
		      model.nuovaPartita(lev,maxTry);
			  // il controller
		      Controller controller = new Controller(model, stage);
			  // la view
		      View view=new View(controller, model);
		  //
		  //Setta lo Stage 
		  stage.setTitle("Password Game"); 
	      stage.setScene(view.getScene());  
		  // Iniziamo..
	      stage.show();
	  } // Hai scelto di terminare...
   } 
} 