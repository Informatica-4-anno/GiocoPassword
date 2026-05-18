package passwordGame;

import javafx.application.Application;

public class Main {
	private static final boolean DEBUG=false;
	
	public static void dprint(String testo) {
		if (DEBUG) System.out.println(testo);
	}
	
	public static void main(String args[]){ 
		Application.launch(App.class, args); 
	} 
}
