package utils.stringConverter;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.IntegerStringConverter;

public class QuantiteIntegerStringConverter extends IntegerStringConverter{
	 
	 @Override
	    public String toString(Integer object) {
			return (object == null) ? null : object.toString();
	    }

	    @Override
	    public Integer fromString(String string) {
	        try {
	            return Integer.parseInt(string);
	        } catch (NumberFormatException e) {
	            Alert alert = new Alert(AlertType.ERROR, "Une quantite est un entier", ButtonType.OK);
	            alert.show();
	        }
	        return null;
	    }
}
