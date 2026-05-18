package passwordGame.grafica;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Dialogs {
	// Help window
	public static void helpWindow(Stage primaryStage) {
        // Crea lo stage per la finestra di dialogo
        Dialog dialog = new Dialog();
        dialog.setTitle("Come si gioca");
        DialogPane dp=dialog.getDialogPane();
        
        
        // Costruisce l'intefaccia        
        
        Image image = new Image("Immagine.png");
        ImageView imageView = new ImageView(image);
        
        // Setta gli stili
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);       
       	      
        //Scene scene = new Scene(ImageView);
        dialog.getDialogPane().setContent(imageView);
        
        // Mostra la finestra e attendi che l'utente la chiuda
        dp.getButtonTypes().addAll(ButtonType.CLOSE);
        dp.setStyle("-fx-font-size: 18px;");
        dialog.showAndWait();
	}
		
	public static int dialogLivello(Stage stage) {
		Dialog<Integer> dialog = new Dialog<>();
		DialogPane dp=dialog.getDialogPane();
		
		dialog.setTitle("Livello di gioco");
		dialog.setHeaderText("Seleziona una delle tre opzioni");

		
		
		ToggleGroup group = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Parole da 5 lettere");
		RadioButton rb2 = new RadioButton("Parole da 6 lettere");
		RadioButton rb3 = new RadioButton("Parole da 7 lettere");

		rb1.setToggleGroup(group);
		rb2.setToggleGroup(group);
		rb3.setToggleGroup(group);

		rb1.setSelected(true);

		ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		VBox content = new VBox(10, rb1, rb2, rb3);
		dp.getButtonTypes().addAll(okButtonType);
		dp.setStyle("-fx-font-size: 18px;");
		dp.setContent(content);
		
		dialog.setResultConverter(buttonType -> {
			if (buttonType == okButtonType) {
				RadioButton selected = (RadioButton) group.getSelectedToggle();
				if (rb1.isSelected()) return 1;
				if (rb2.isSelected()) return 2;
				if (rb3.isSelected()) return 3;
			}
			return null;
		});
		 
		return dialog.showAndWait().orElse(-1);
	}		

	public static void looseDialog(String secret) {
	    Alert alert = new Alert(Alert.AlertType.NONE);
	    alert.setTitle("Fine Partita");
	    alert.setHeaderText(null);
	    alert.getButtonTypes().setAll(ButtonType.OK);

	    Text t1 = new Text("Hai perso la parola segreta era ");
	    Text t2 = new Text(secret);
	    t1.setStyle("-fx-font-size: 18px;");
	    t2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: red;");
	    alert.getDialogPane().setStyle("-fx-font-size: 18px;");
	    TextFlow textFlow = new TextFlow(t1, t2);
	    textFlow.setPrefWidth(400);
	    alert.getDialogPane().setContent(textFlow);
	    alert.showAndWait();   
	}

	public static void winDialog() {
	    Alert alert = new Alert(Alert.AlertType.NONE);
	    alert.setTitle("Fine Partita");
	    alert.setHeaderText(null);
	    alert.getButtonTypes().setAll(ButtonType.OK);
	    Text t1 = new Text("   Complimenti hai VINTO!!   ");
	    alert.getDialogPane().setStyle("-fx-font-size: 18px;");
	    t1.setStyle("-fx-font-size: 30px;");
	    alert.getDialogPane().setContent(t1);
	    alert.showAndWait();
	}
	
	public static void notValidDialog() {
	    Alert alert = new Alert(Alert.AlertType.NONE);
	    alert.setTitle("Parola non valida");
	    alert.setHeaderText(null);
	    alert.getButtonTypes().setAll(ButtonType.OK);
	    Text t1 = new Text("  La parola inserita non è una parola corretta in italiano  ");
	    alert.getDialogPane().setStyle("-fx-font-size: 18px;");
	    t1.setStyle("-fx-font-size: 18px;");
	    alert.getDialogPane().setContent(t1);
	    alert.showAndWait();
	}
}
