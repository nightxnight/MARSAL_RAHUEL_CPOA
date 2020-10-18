package vue.application.management;

import java.util.ArrayList;

import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public interface Management<T>{
	
	public abstract TableView<T> getTableModel();
	public abstract ArrayList<T> getDatas();
	public abstract Pane getActionPane(T objet, boolean bool);
}
