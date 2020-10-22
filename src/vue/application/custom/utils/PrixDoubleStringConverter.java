package vue.application.custom.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.DoubleStringConverter;

public class PrixDoubleStringConverter extends DoubleStringConverter{

	 private final DoubleStringConverter converter = new DoubleStringConverter();
	 
	 @Override
	    public String toString(Double object) {
			return (object == null) ? null : object.toString();
	    }

	    @Override
	    public Double fromString(String string) {
	        try {
	            return Double.parseDouble(string);
	        } catch (NumberFormatException e) {
	            Alert alert = new Alert(AlertType.ERROR, "Un prix est un reel", ButtonType.OK);
	            alert.show();
	        }
	        return null;
	    }
}
