package vue.application.custom.alert;

import controleur.MainControleur;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ErrorAlert extends Alert {

	private final ButtonType OK = new ButtonType("Ok");
	
	public ErrorAlert(String title, String message, MainControleur parent) {
		super(AlertType.ERROR, message);
		this.setTitle(title);   
	    this.getButtonTypes().clear();
	    this.getButtonTypes().addAll(OK);
	    
	    this.setGraphic(new ImageView("file:resources/images/errorIcon.png"));
	    
	    Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/images/errorIcon.png"));
        stage.centerOnScreen();
        
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.getStylesheets().add(parent.getScene().getStylesheets().get(0));
        dialogPane.getStyleClass().add("dialog-pane");
	}
	
	public ButtonType getOK() {
		return OK;
	}
}