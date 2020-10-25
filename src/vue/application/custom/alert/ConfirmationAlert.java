package vue.application.custom.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmationAlert extends Alert{
	
	private final ButtonType VALIDER = new ButtonType("Valider");
	private final ButtonType ANNULER = new ButtonType("Annuler");

	public ConfirmationAlert(String title, String message) {
		super(AlertType.CONFIRMATION, message);
        this.setTitle(title);   
        this.getButtonTypes().clear();
        this.getButtonTypes().addAll(VALIDER, ANNULER);
	}
	
	public ButtonType getValider() {
		return VALIDER;
	}
	
	public ButtonType getAnnuler() {
		return ANNULER;
	}
}
